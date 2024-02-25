package cn.nukkit.convert.leveldb;

import cn.nukkit.Player;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockstate.BlockState;
import cn.nukkit.blockstate.BlockStateRegistry;
import cn.nukkit.convert.palette.Palette;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.DimensionData;
import cn.nukkit.level.format.Chunk;
import cn.nukkit.level.format.ChunkSection;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.iq80.leveldb.WriteBatch;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Collection;
import java.util.Objects;

/**
 * Allay Project 8/23/2023
 *
 * @author Cool_Loong
 */
@Slf4j
public class LevelDBChunkSerializer {
    public static final LevelDBChunkSerializer INSTANCE = new LevelDBChunkSerializer();

    private LevelDBChunkSerializer() {
    }

    public void serialize(WriteBatch writeBatch, Chunk chunk, DimensionData dimensionData) {
        try {
            writeBatch.put(LevelDBKeyUtil.VERSION.getKey(chunk.getX(), chunk.getZ(), dimensionData), new byte[]{40});
            writeBatch.put(LevelDBKeyUtil.CHUNK_FINALIZED_STATE.getKey(chunk.getX(), chunk.getZ(), dimensionData), Unpooled.buffer(4).writeIntLE(2).array());
            writeBatch.put(LevelDBKeyUtil.PNX_EXTRA_DATA.getKey(chunk.getX(), chunk.getZ(), dimensionData), NBTIO.write(new CompoundTag()));
            serializeBlock(writeBatch, chunk, dimensionData);
            serializeHeightAndBiome(writeBatch, chunk, dimensionData);
            serializeTileAndEntity(writeBatch, chunk, dimensionData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static int index(int x, int y, int z) {
        //The bedrock chunk order is xzy,the chunk order of java version is yzx
        return (x << 8) + (z << 4) + y;
    }

    //serialize chunk section
    private void serializeBlock(WriteBatch writeBatch, Chunk chunk, DimensionData dimensionData) {
        ChunkSection[] sections = chunk.getSections();
        for (var section : sections) {
            if (section == null) {
                continue;
            }
            ByteBuf buffer = ByteBufAllocator.DEFAULT.ioBuffer();
            try {
                buffer.writeByte(9);
                buffer.writeByte(2);
                int y = section.getY();
                buffer.writeByte(y);
                BlockStateRegistry.Registration registration = BlockStateRegistry.getRegistration(BlockState.AIR);
                CompoundTag originalBlock = registration.originalBlock;
                Palette<CompoundTag> compoundTagPalette = new Palette<CompoundTag>(originalBlock);
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 16; j++) {
                        for (int k = 0; k < 16; k++) {
                            for (int l = 0; l < 16; l++) {
                                BlockState blockState = section.getBlockState(j, k, l, i);
                                compoundTagPalette.set(index(j, k, l), BlockStateRegistry.getRegistration(blockState).originalBlock);
                            }
                        }
                    }
                    compoundTagPalette.writeToStoragePersistent(buffer, s -> s);
                }
                writeBatch.put(LevelDBKeyUtil.CHUNK_SECTION_PREFIX.getKey(chunk.getX(), chunk.getZ(), y, dimensionData), Utils.convertByteBuf2Array(buffer));
            } finally {
                buffer.release();
            }
        }
    }

    //write biomeAndHeight
    private void serializeHeightAndBiome(WriteBatch writeBatch, Chunk chunk, DimensionData dimensionData) {
        //Write biomeAndHeight
        ByteBuf heightAndBiomesBuffer = ByteBufAllocator.DEFAULT.ioBuffer();
        try {
            for (short height : chunk.getNewHeightMapArray()) {
                heightAndBiomesBuffer.writeShortLE(height);
            }
            for (int ySection = 0; ySection < dimensionData.getChunkSectionCount(); ySection++) {
                ChunkSection section = chunk.getSection(ySection);
                if (section == null) continue;
                Palette<Integer> palette = new Palette<>(1);
                if (section instanceof cn.nukkit.level.format.anvil.ChunkSection chunkSection) {
                    for (int j = 0; j < 16; j++) {
                        for (int k = 0; k < 16; k++) {
                            for (int l = 0; l < 16; l++) {
                                int id = chunkSection.getBiomeId(j, k, l);
                                palette.set(index(j, k, l), id);
                            }
                        }
                    }
                } else {
                    for (int j = 0; j < 16; j++) {
                        for (int k = 0; k < 16; k++) {
                            for (int l = 0; l < 16; l++) {
                                palette.set(index(j, k, l), 1);
                            }
                        }
                    }
                }
                palette.writeToStorageRuntime(heightAndBiomesBuffer, Integer::intValue);
            }
            if (heightAndBiomesBuffer.readableBytes() > 0) {
                writeBatch.put(LevelDBKeyUtil.DATA_3D.getKey(chunk.getX(), chunk.getZ(), dimensionData), Utils.convertByteBuf2Array(heightAndBiomesBuffer));
            }
        } finally {
            heightAndBiomesBuffer.release();
        }
    }

    private void serializeTileAndEntity(WriteBatch writeBatch, Chunk chunk, DimensionData dimensionData) {
        //Write blockEntities
        Collection<BlockEntity> blockEntities = chunk.getBlockEntities().values();
        ByteBuf tileBuffer = ByteBufAllocator.DEFAULT.ioBuffer();
        try (var bufStream = new ByteBufOutputStream(tileBuffer)) {
            byte[] key = LevelDBKeyUtil.BLOCK_ENTITIES.getKey(chunk.getX(), chunk.getZ(), dimensionData);
            if (blockEntities.isEmpty()) writeBatch.delete(key);
            else {
                for (BlockEntity blockEntity : blockEntities) {
                    blockEntity.saveNBT();
                    NBTIO.write(blockEntity.namedTag, bufStream, ByteOrder.LITTLE_ENDIAN);
                }
                writeBatch.put(key, Utils.convertByteBuf2Array(tileBuffer));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            tileBuffer.release();
        }

        Collection<Entity> entities = chunk.getEntities().values();
        ByteBuf entityBuffer = ByteBufAllocator.DEFAULT.ioBuffer();
        try (var bufStream = new ByteBufOutputStream(entityBuffer)) {
            byte[] key = LevelDBKeyUtil.ENTITIES.getKey(chunk.getX(), chunk.getZ(), dimensionData);
            if (entities.isEmpty()) {
                writeBatch.delete(key);
            } else {
                for (Entity e : entities) {
                    if (!(e instanceof Player) && !e.closed && e.canBeSavedWithChunk()) {
                        e.saveNBT();
                        String string = Objects.requireNonNull(e.getIdentifier()).toString();
                        CompoundTag namedTag = e.namedTag;
                        namedTag.remove("id");
                        namedTag.putString("identifier", string);
                        NBTIO.write(namedTag, bufStream, ByteOrder.LITTLE_ENDIAN);
                    }
                }
                writeBatch.put(key, Utils.convertByteBuf2Array(entityBuffer));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            entityBuffer.release();
        }
    }
}

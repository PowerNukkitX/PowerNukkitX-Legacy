package cn.nukkit.convert.leveldb;

import cn.nukkit.level.DimensionData;
import cn.nukkit.level.GameRule;
import cn.nukkit.level.GameRules;
import cn.nukkit.level.format.anvil.Chunk;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.BlockVector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.IntTag;
import cn.nukkit.network.protocol.types.GameType;
import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.WriteBatch;

import java.io.*;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class LevelDBStorage {
    private final DB db;
    private final String path;

    public LevelDBStorage(String path) throws IOException {
        this(path, new Options()
                .createIfMissing(true)
                .compressionType(CompressionType.ZLIB_RAW)
                .blockSize(64 * 1024));
    }

    public LevelDBStorage(String pathFolder, Options options) throws IOException {
        this.path = pathFolder;
        Path path = Path.of(pathFolder);
        File folder = path.toFile();
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!folder.isDirectory()) throw new IllegalArgumentException("The path must be a folder");

        File dbFolder = path.resolve("db").toFile();
        if (!dbFolder.exists()) dbFolder.mkdirs();
        db = net.daporkchop.ldbjni.LevelDB.PROVIDER.open(dbFolder, options);
    }

    public void writeChunk(Chunk chunk, DimensionData dimensionData) throws IOException {
        try (WriteBatch writeBatch = this.db.createWriteBatch()) {
            LevelDBChunkSerializer.INSTANCE.serialize(writeBatch, chunk, dimensionData);
            this.db.write(writeBatch);
        }
    }

    public synchronized void close() {
        try {
            db.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final byte[] levelDatMagic = new byte[]{10, 0, 0, 0, 68, 11, 0, 0};

    public static void writeLevelDat(String pathName, DimensionData dimensionData, LevelDat levelDat) {
        Path path = Path.of(pathName);
        String levelDatName = "level.dat";
        if (dimensionData.getDimensionId() != 0) {
            levelDatName = "level_Dim%s.dat".formatted(dimensionData.getDimensionId());
        }
        var levelDatNow = path.resolve(levelDatName).toFile();
        try (var output = new FileOutputStream(levelDatNow)) {
            if (levelDatNow.exists()) {
                Files.copy(path.resolve(levelDatName), path.resolve(levelDatName + "_old"), StandardCopyOption.REPLACE_EXISTING);
            } else {
                levelDatNow.createNewFile();
            }
            output.write(levelDatMagic);//magic number
            NBTIO.write(createWorldDataNBT(levelDat), output, ByteOrder.LITTLE_ENDIAN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CompoundTag createWorldDataNBT(LevelDat worldData) {
        CompoundTag levelDat = new CompoundTag();
        levelDat.putString("BiomeOverride", worldData.getBiomeOverride());
        levelDat.putBoolean("CenterMapsToOrigin", worldData.isCenterMapsToOrigin());
        levelDat.putBoolean("ConfirmedPlatformLockedContent", worldData.isConfirmedPlatformLockedContent());
        levelDat.putInt("Difficulty", worldData.getDifficulty());
        levelDat.putString("FlatWorldLayers", worldData.getFlatWorldLayers());
        levelDat.putBoolean("ForceGameType", worldData.isForceGameType());
        levelDat.putInt("GameType", worldData.getGameType().ordinal());
        levelDat.putInt("Generator", worldData.getGenerator());
        levelDat.putString("InventoryVersion", worldData.getInventoryVersion());
        levelDat.putBoolean("LANBroadcast", worldData.isLANBroadcast());
        levelDat.putBoolean("LANBroadcastIntent", worldData.isLANBroadcastIntent());
        levelDat.putLong("LastPlayed", worldData.getLastPlayed());
        levelDat.putString("LevelName", worldData.getName());
        levelDat.putInt("LimitedWorldOriginX", worldData.getLimitedWorldOriginPoint().getX());
        levelDat.putInt("LimitedWorldOriginY", worldData.getLimitedWorldOriginPoint().getY());
        levelDat.putInt("LimitedWorldOriginZ", worldData.getLimitedWorldOriginPoint().getZ());
        levelDat.putList("MinimumCompatibleClientVersion", worldData.getMinimumCompatibleClientVersion().toTag());
        levelDat.putList("lastOpenedWithVersion", worldData.getLastOpenedWithVersion().toTag());
        levelDat.putBoolean("MultiplayerGame", worldData.isMultiplayerGame());
        levelDat.putBoolean("MultiplayerGameIntent", worldData.isMultiplayerGameIntent());
        levelDat.putInt("NetherScale", worldData.getNetherScale());
        levelDat.putInt("NetworkVersion", worldData.getNetworkVersion());
        levelDat.putInt("Platform", worldData.getPlatform());
        levelDat.putInt("PlatformBroadcastIntent", worldData.getPlatformBroadcastIntent());
        levelDat.putLong("RandomSeed", worldData.getRandomSeed());
        levelDat.putBoolean("SpawnV1Villagers", worldData.isSpawnV1Villagers());
        levelDat.putInt("SpawnX", worldData.getSpawnPoint().getX());
        levelDat.putInt("SpawnY", worldData.getSpawnPoint().getY());
        levelDat.putInt("SpawnZ", worldData.getSpawnPoint().getZ());
        levelDat.putInt("StorageVersion", worldData.getStorageVersion());
        levelDat.putLong("Time", worldData.getTime());
        levelDat.putInt("WorldVersion", worldData.getWorldVersion());
        levelDat.putInt("XBLBroadcastIntent", worldData.getXBLBroadcastIntent());
        CompoundTag abilities = new CompoundTag()
                .putBoolean("attackmobs", worldData.getAbilities().isAttackMobs())
                .putBoolean("attackplayers", worldData.getAbilities().isAttackPlayers())
                .putBoolean("build", worldData.getAbilities().isBuild())
                .putBoolean("doorsandswitches", worldData.getAbilities().isDoorsAndSwitches())
                .putBoolean("flying", worldData.getAbilities().isFlying())
                .putBoolean("instabuild", worldData.getAbilities().isInstaBuild())
                .putBoolean("invulnerable", worldData.getAbilities().isInvulnerable())
                .putBoolean("lightning", worldData.getAbilities().isLightning())
                .putBoolean("mayfly", worldData.getAbilities().isMayFly())
                .putBoolean("mine", worldData.getAbilities().isMine())
                .putBoolean("op", worldData.getAbilities().isOp())
                .putBoolean("opencontainers", worldData.getAbilities().isOpenContainers())
                .putBoolean("teleport", worldData.getAbilities().isTeleport())
                .putFloat("flySpeed", worldData.getAbilities().getFlySpeed())
                .putFloat("walkSpeed", worldData.getAbilities().getWalkSpeed());
        CompoundTag experiments = new CompoundTag()
                .putBoolean("cameras", worldData.getExperiments().isCameras())
                .putBoolean("data_driven_biomes", worldData.getExperiments().isDataDrivenBiomes())
                .putBoolean("data_driven_items", worldData.getExperiments().isDataDrivenItems())
                .putBoolean("experimental_molang_features", worldData.getExperiments().isExperimentalMolangFeatures())
                .putBoolean("experiments_ever_used", worldData.getExperiments().isExperimentsEverUsed())
                .putBoolean("gametest", worldData.getExperiments().isGametest())
                .putBoolean("saved_with_toggled_experiments", worldData.getExperiments().isSavedWithToggledExperiments())
                .putBoolean("upcoming_creator_features", worldData.getExperiments().isUpcomingCreatorFeatures())
                .putBoolean("villager_trades_rebalance", worldData.getExperiments().isVillagerTradesRebalance());
        levelDat.put("abilities", abilities);
        levelDat.put("experiments", experiments);

        levelDat.putBoolean("bonusChestEnabled", worldData.isBonusChestEnabled());
        levelDat.putBoolean("bonusChestSpawned", worldData.isBonusChestSpawned());
        levelDat.putBoolean("cheatsEnabled", worldData.isCheatsEnabled());
        levelDat.putBoolean("commandsEnabled", worldData.isCommandsEnabled());
        levelDat.putLong("currentTick", worldData.getCurrentTick());
        levelDat.putInt("daylightCycle", worldData.getDaylightCycle());
        levelDat.putInt("editorWorldType", worldData.getEditorWorldType());
        levelDat.putInt("eduOffer", worldData.getEduOffer());
        levelDat.putBoolean("educationFeaturesEnabled", worldData.isEducationFeaturesEnabled());

        levelDat.put("commandblockoutput", worldData.getGameRules().getGameRules().get(GameRule.COMMAND_BLOCK_OUTPUT).getTag());
        levelDat.put("commandblocksenabled", worldData.getGameRules().getGameRules().get(GameRule.COMMAND_BLOCKS_ENABLED).getTag());
        levelDat.put("dodaylightcycle", worldData.getGameRules().getGameRules().get(GameRule.DO_DAYLIGHT_CYCLE).getTag());
        levelDat.put("doentitydrops", worldData.getGameRules().getGameRules().get(GameRule.DO_ENTITY_DROPS).getTag());
        levelDat.put("dofiretick", worldData.getGameRules().getGameRules().get(GameRule.DO_FIRE_TICK).getTag());
        levelDat.put("doimmediaterespawn", worldData.getGameRules().getGameRules().get(GameRule.DO_IMMEDIATE_RESPAWN).getTag());
        levelDat.put("doinsomnia", worldData.getGameRules().getGameRules().get(GameRule.DO_INSOMNIA).getTag());
        levelDat.putBoolean("dolimitedcrafting", false);
        levelDat.put("domobloot", worldData.getGameRules().getGameRules().get(GameRule.DO_MOB_LOOT).getTag());
        levelDat.put("domobspawning", worldData.getGameRules().getGameRules().get(GameRule.DO_MOB_SPAWNING).getTag());
        levelDat.put("dotiledrops", worldData.getGameRules().getGameRules().get(GameRule.DO_TILE_DROPS).getTag());
        levelDat.put("doweathercycle", worldData.getGameRules().getGameRules().get(GameRule.DO_WEATHER_CYCLE).getTag());
        levelDat.put("drowningdamage", worldData.getGameRules().getGameRules().get(GameRule.DROWNING_DAMAGE).getTag());
        levelDat.put("falldamage", worldData.getGameRules().getGameRules().get(GameRule.FALL_DAMAGE).getTag());
        levelDat.put("firedamage", worldData.getGameRules().getGameRules().get(GameRule.FIRE_DAMAGE).getTag());
        levelDat.put("freezedamage", worldData.getGameRules().getGameRules().get(GameRule.FREEZE_DAMAGE).getTag());
        levelDat.put("functioncommandlimit", worldData.getGameRules().getGameRules().get(GameRule.FUNCTION_COMMAND_LIMIT).getTag());
        levelDat.put("keepinventory", worldData.getGameRules().getGameRules().get(GameRule.KEEP_INVENTORY).getTag());
        levelDat.put("maxcommandchainlength", worldData.getGameRules().getGameRules().get(GameRule.MAX_COMMAND_CHAIN_LENGTH).getTag());
        levelDat.put("mobgriefing", worldData.getGameRules().getGameRules().get(GameRule.MOB_GRIEFING).getTag());
        levelDat.put("naturalregeneration", worldData.getGameRules().getGameRules().get(GameRule.NATURAL_REGENERATION).getTag());
        levelDat.put("pvp", worldData.getGameRules().getGameRules().get(GameRule.PVP).getTag());
        levelDat.putBoolean("respawnblocksexplode", false);
        levelDat.put("sendcommandfeedback", worldData.getGameRules().getGameRules().get(GameRule.SEND_COMMAND_FEEDBACK).getTag());
        levelDat.putBoolean("showbordereffect", false);
        levelDat.put("showcoordinates", worldData.getGameRules().getGameRules().get(GameRule.SHOW_COORDINATES).getTag());
        levelDat.put("showdeathmessages", worldData.getGameRules().getGameRules().get(GameRule.SHOW_DEATH_MESSAGES).getTag());
        levelDat.put("showtags", worldData.getGameRules().getGameRules().get(GameRule.SHOW_TAGS).getTag());
        levelDat.put("spawnradius", worldData.getGameRules().getGameRules().get(GameRule.SPAWN_RADIUS).getTag());
        levelDat.put("tntexplodes", worldData.getGameRules().getGameRules().get(GameRule.TNT_EXPLODES).getTag());

        //PNX Custom field
        levelDat.putBoolean("raining", worldData.isRaining());
        levelDat.putBoolean("thundering", worldData.isThundering());
        return levelDat;
    }
}

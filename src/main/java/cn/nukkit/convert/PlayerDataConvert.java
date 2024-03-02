package cn.nukkit.convert;

import cn.nukkit.Server;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import com.google.common.collect.HashBiMap;
import org.iq80.leveldb.CompressionType;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.impl.Iq80DBFactory;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class PlayerDataConvert {
    static DB db;


    public static void start() {
        Server server = Server.getInstance();
        File output = new File(server.getDataPath(), "output/players");
        if (!output.exists()) {
            output.mkdirs();
        }
        try {
            db = Iq80DBFactory.factory.open(output, new Options()
                    .createIfMissing(true)
                    .compressionType(CompressionType.ZLIB_RAW));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DB oldDB = Server.getInstance().nameLookup;
        HashBiMap<String, UUID> name2uuid = HashBiMap.create();
        File file = new File(server.getDataPath() + "players");
        for (var f : Objects.requireNonNull(file.listFiles(fi -> fi.getName().endsWith(".dat")))) {
            String sUuid = f.getName().replace(".dat", "");
            UUID uuid = UUID.fromString(sUuid);
            ByteBuffer buffer = ByteBuffer.allocate(16);
            buffer.putLong(uuid.getMostSignificantBits());
            buffer.putLong(uuid.getLeastSignificantBits());
            byte[] v = buffer.array();
            for (java.util.Map.Entry<byte[], byte[]> entry : oldDB) {
                if (Arrays.equals(entry.getValue(), v)) {
                    String s = new String(entry.getKey(), StandardCharsets.UTF_8);
                    name2uuid.put(s, uuid);
                    break;
                }
            }
        }
        for (var f : Objects.requireNonNull(file.listFiles(fi -> fi.getName().endsWith(".dat")))) {
            UUID uuid = UUID.fromString(f.getName().replace(".dat", ""));
            CompoundTag offlinePlayerData = server.getOfflinePlayerData(uuid, false);
            convertInventory(offlinePlayerData);
            String s = name2uuid.inverse().get(uuid);

            ByteBuffer buffer = ByteBuffer.allocate(16);
            buffer.putLong(uuid.getMostSignificantBits());
            buffer.putLong(uuid.getLeastSignificantBits());
            byte[] v = buffer.array();

            db.put(s.getBytes(StandardCharsets.UTF_8), v);
            try {
                db.put(v, NBTIO.writeGZIPCompressed(offlinePlayerData, ByteOrder.BIG_ENDIAN));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            db.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void convertInventory(CompoundTag root) {
        ListTag<CompoundTag> inventory = root.getList("Inventory", CompoundTag.class);
        ListTag<CompoundTag> result = new ListTag<>();
        CompoundTag offHand = null;
        for (var nbt : inventory.getAll()) {
            int id = nbt.getShort("id");
            int slot = nbt.getByte("Slot");
            int count = nbt.getByte("Count");
            int damage = nbt.getShort("Damage");

            if (slot >= 9 && slot < 45) {
                Item item = Item.get(id);
                String namespaceId = item.getNamespaceId();
                CompoundTag newTag = new CompoundTag()
                        .putByte("Count", count)
                        .putShort("Damage", damage);
                newTag.putString("Name", namespaceId);
                newTag.putByte("Slot", slot - 9);
                if (item.hasCompoundTag()) {
                    newTag.putCompound("tag", item.getNamedTag());
                }
                if (item.getBlockUnsafe() != null) {
                    newTag.putCompound("Block", NBTIO.putBlockHelper(item.getBlockUnsafe()));
                }
                result.add(newTag);
            } else if (slot < 104 && slot >= 100) {//armor
                Item item = Item.get(id);
                String namespaceId = item.getNamespaceId();
                CompoundTag newTag = new CompoundTag()
                        .putByte("Count", count)
                        .putShort("Damage", damage);
                newTag.putString("Name", namespaceId);
                newTag.putByte("Slot", slot - 64);
                if (item.hasCompoundTag()) {
                    newTag.putCompound("tag", item.getNamedTag());
                }
                if (item.getBlockUnsafe() != null) {
                    newTag.putCompound("Block", NBTIO.putBlockHelper(item.getBlockUnsafe()));
                }
                result.add(newTag);
            } else if (slot == -106) {//offhand
                offHand = new CompoundTag();
                Item item = Item.get(id);
                String namespaceId = item.getNamespaceId();
                offHand.putByte("Count", count)
                        .putShort("Damage", damage);
                offHand.putString("Name", namespaceId);
                offHand.putByte("Slot", 0);
                if (item.hasCompoundTag()) {
                    offHand.putCompound("tag", item.getNamedTag());
                }
                if (item.getBlockUnsafe() != null) {
                    offHand.putCompound("Block", NBTIO.putBlockHelper(item.getBlockUnsafe()));
                }
            }
        }
        if (offHand != null) {
            root.putCompound("OffInventory", offHand);
        }
        if (result.size() != 0) {
            root.putList("Inventory", result);
        }

        ListTag<CompoundTag> enderItems = root.getList("EnderItems", CompoundTag.class);
        ListTag<CompoundTag> resultEnderItems = new ListTag<>();
        for (var nbt : enderItems.getAll()) {
            int id = nbt.getShort("id");
            int slot = nbt.getByte("Slot");
            int count = nbt.getByte("Count");
            int damage = nbt.getShort("Damage");

            if (slot >= 0 && slot < 27) {
                Item item = Item.get(id);
                String namespaceId = item.getNamespaceId();
                CompoundTag newTag = new CompoundTag()
                        .putByte("Count", count)
                        .putShort("Damage", damage);
                newTag.putString("Name", namespaceId);
                newTag.putByte("Slot", slot);
                if (item.hasCompoundTag()) {
                    newTag.putCompound("tag", item.getNamedTag());
                }
                if (item.getBlockUnsafe() != null) {
                    newTag.putCompound("Block", NBTIO.putBlockHelper(item.getBlockUnsafe()));
                }
                resultEnderItems.add(newTag);
            }
        }
        if (resultEnderItems.size() != 0) {
            root.putList("EnderItems", resultEnderItems);
        }
    }
}

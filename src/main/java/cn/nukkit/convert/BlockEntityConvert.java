package cn.nukkit.convert;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;

public class BlockEntityConvert {
    public static void convertInventory(CompoundTag root) {
        ListTag<CompoundTag> items = root.getList("Items", CompoundTag.class);
        ListTag<CompoundTag> result = new ListTag<>();
        for (var nbt : items.getAll()) {
            int id = nbt.getShort("id");
            int slot = nbt.getByte("Slot");
            int count = nbt.getByte("Count");
            int damage = nbt.getShort("Damage");

            var newTag = new CompoundTag();
            Item item = Item.get(id);
            String namespaceId = item.getNamespaceId();
            newTag.putByte("Count", count)
                    .putShort("Damage", damage);
            newTag.putString("Name", namespaceId);
            newTag.putByte("Slot", slot);
            if (item.hasCompoundTag()) {
                newTag.putCompound("tag", item.getNamedTag());
            }
            if (item.getBlockUnsafe() != null) {
                newTag.putCompound("Block", NBTIO.putBlockHelper(item.getBlockUnsafe()));
            }
            result.add(newTag);
        }
        if (result.size() != 0) {
            root.putList("Items", result);
        }
    }
}

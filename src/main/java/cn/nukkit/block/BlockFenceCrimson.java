package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;

@PowerNukkitOnly
@Since("1.4.0.0-PN")
public class BlockFenceCrimson extends BlockFenceBase {

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    public BlockFenceCrimson() {
        this(0);
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    public BlockFenceCrimson(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Crimson Fence";
    }

    @Override
    public int getId() {
        return CRIMSON_FENCE;
    }

    @Override
    public int getBurnChance() {
        return 0;
    }
    
    @Override
    public int getBurnAbility() {
        return 0;
    }

}

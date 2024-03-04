package cn.nukkit.convert.task;

import cn.nukkit.convert.leveldb.LevelDBStorage;
import cn.nukkit.level.DimensionData;
import cn.nukkit.level.format.anvil.Chunk;
import cn.nukkit.level.format.generic.BaseRegionLoader;
import lombok.extern.slf4j.Slf4j;
import me.tongfei.progressbar.ProgressBar;

import java.io.IOException;
import java.util.concurrent.ForkJoinTask;

@Slf4j
public class ConvertTask extends ForkJoinTask {
    final BaseRegionLoader regionLoader;
    final LevelDBStorage levelDBStorage;
    final DimensionData dimensionData;

    public ConvertTask(DimensionData dimensionData, LevelDBStorage levelDBStorage, BaseRegionLoader baseRegionLoader) {
        this.dimensionData = dimensionData;
        this.levelDBStorage = levelDBStorage;
        this.regionLoader = baseRegionLoader;
        log.info("Starting convert region" + baseRegionLoader.getX() + ":" + baseRegionLoader.getZ());
    }

    @Override
    public Object getRawResult() {
        return null;
    }

    @Override
    protected void setRawResult(Object value) {
    }

    @Override
    protected boolean exec() {
        // try-with-resource block
        try (ProgressBar pb = new ProgressBar("Task" + regionLoader.getX() + ":" + regionLoader.getZ(), 1024)) { // name, initial max
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 32; j++) {
                    if (regionLoader.chunkExists(i, j)) {
                        try {
                            Chunk chunk = (Chunk) regionLoader.readChunk(i, j);
                            if (chunk != null) {
                                chunk.initChunk();
                                levelDBStorage.writeChunk(chunk, dimensionData);
                            }
                            chunk = null;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    pb.step();
                }
            }
        } // progress bar stops automatically after completion of try-with-resource block
        try {
            regionLoader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}

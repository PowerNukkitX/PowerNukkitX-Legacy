package cn.nukkit.convert;

import cn.nukkit.Server;
import cn.nukkit.convert.leveldb.LevelDBStorage;
import cn.nukkit.convert.leveldb.LevelDat;
import cn.nukkit.convert.task.ConvertTask;
import cn.nukkit.lang.PluginI18n;
import cn.nukkit.level.DimensionData;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.anvil.Anvil;
import cn.nukkit.level.format.generic.BaseRegionLoader;
import cn.nukkit.network.protocol.types.GameType;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

@Slf4j
public class Convert {
    public static String world = "world";
    public static PluginI18n I18N;
    public static ForkJoinPool THREAD_POOL_EXECUTOR = (ForkJoinPool) Executors.newWorkStealingPool();

    public static void start() {
        PlayerDataConvert.start();
        log.info("convert player data complete!");

        File file = new File("worlds", world + "/region");
        Level level = Server.getInstance().getLevelByName(world);
        Anvil levelProvider = (Anvil) level.requireProvider();

        try {
            DimensionData dimensionData = levelProvider.getDimensionData();
            String path = "output/worlds/" + level.getName();
            LevelDat build = LevelDat.builder().spawnPoint(level.getSpawnLocation().asBlockVector3()).randomSeed(level.getSeed()).name(level.getName()).gameRules(level.getGameRules())
                    .gameType(GameType.SURVIVAL).build();
            LevelDBStorage levelDBStorage = new LevelDBStorage(path);
            LevelDBStorage.writeLevelDat(path, dimensionData, build);
            List<ForkJoinTask> tasks = new ArrayList<>();
            for (var r : Objects.requireNonNull(file.listFiles(f -> f.isFile() && f.getName().endsWith(".mca")))) {
                String name = r.getName();
                String[] split = name.split("\\.");
                int x = Integer.parseInt(split[1]);
                int z = Integer.parseInt(split[2]);
                try {
                    levelProvider.loadRegion(x, z);
                } catch (Exception e) {
                    log.error("load region {} {} error", x, z);
                }
                BaseRegionLoader region = levelProvider.getRegion(x, z);
                if (region != null) {
                    tasks.add(THREAD_POOL_EXECUTOR.submit(new ConvertTask(dimensionData, levelDBStorage, region)));
                } else {
                    log.error("region {} {} is null", x, z);
                }
            }
            for (var task : tasks) {
                task.get();
            }
            levelDBStorage.close();
            log.info("All region is complete!");
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

package cn.nukkit.convert.bitarray;


/**
 * Allay Project 2023/4/14
 *
 * @author JukeboxMC | daoge_cmd
 */
public interface BitArray {

    void set(int index, int value);

    int get(int index);

    int size();

    int[] words();

    BitArrayVersion version();

    BitArray copy();
}
package cn.nukkit.network.protocol;

import cn.nukkit.item.Item;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author joserobjr
 * @since 2021-09-25
 */
class CraftingEventPacketTest {

    @Test
    void encodeDecode() {
        var packet = new CraftingEventPacket();
        packet.type = 1;
        packet.id = UUID.randomUUID();
        packet.input = new Item[]{new Item(500), new Item(501)};
        packet.output = new Item[]{new Item(502), new Item(503)};

        packet.encode();
        var packet2 = new CraftingEventPacket();
        packet2.setBuffer(packet.getBuffer());
        packet2.decode();

        assertEquals(packet, packet2);
    }
}

package cn.nukkit.network;

import cn.nukkit.api.PowerNukkitXOnly;
import cn.nukkit.api.Since;
import cn.nukkit.network.protocol.types.PacketCompressionAlgorithm;
import cn.nukkit.utils.BinaryStream;
import cn.nukkit.utils.SnappyCompression;

@Since("1.19.30-r1")
@PowerNukkitXOnly
public interface CompressionProvider {

    CompressionProvider NONE = new CompressionProvider() {
        @Override
        public byte[] compress(BinaryStream packet, int level) throws Exception {
            return packet.getBuffer();
        }

        @Override
        public byte[] decompress(byte[] compressed) throws Exception {
            return compressed;
        }

        @Override
        public byte getPrefix() {
            return (byte) 0xff;
        }
    };

    CompressionProvider ZLIB = new CompressionProvider() {
        @Override
        public byte[] compress(BinaryStream packet, int level) throws Exception {
            return Network.deflateRaw(packet.getBuffer(), level);
        }

        @Override
        public byte[] decompress(byte[] compressed) throws Exception {
            return Network.inflateRaw(compressed);
        }

        @Override
        public byte getPrefix() {
            return (byte) 0x00;
        }
    };

    @PowerNukkitXOnly
    @Since("1.20.0-r2")
    CompressionProvider SNAPPY = new CompressionProvider() {
        @Override
        public byte[] compress(BinaryStream packet, int level) throws Exception {
            return SnappyCompression.compress(packet.getBuffer());
        }

        @Override
        public byte[] decompress(byte[] compressed) throws Exception {
            return SnappyCompression.decompress(compressed);
        }
    };


    byte[] compress(BinaryStream packet, int level) throws Exception;

    byte[] decompress(byte[] compressed) throws Exception;

    static CompressionProvider from(PacketCompressionAlgorithm algorithm) {
        if (algorithm == null) {
            return NONE;
        } else if (algorithm == PacketCompressionAlgorithm.ZLIB) {
            return ZLIB;
        } else if (algorithm == PacketCompressionAlgorithm.SNAPPY) {
            return SNAPPY;
        }
        throw new UnsupportedOperationException();
    }


    default byte getPrefix() {
        throw new UnsupportedOperationException();
    }

    static CompressionProvider byPrefix(byte prefix) {
        switch (prefix) {//todo support SNAPPY
            case 0x00:
                return ZLIB;
            case (byte) 0xff:
                return NONE;
        }
        throw new IllegalArgumentException("Unsupported compression type: " + prefix);
    }
}
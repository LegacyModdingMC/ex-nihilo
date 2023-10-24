package exnihilo.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessageSieve implements IMessage {

    public int x;
    public int y;
    public int z;

    public float progress;
    public int meshType;

    public String blockName;
    public int blockMeta;

    public MessageSieve() {}

    public MessageSieve(int x, int y, int z, float progress, int meshType, int blockMeta, String blockName) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.progress = progress;
        this.meshType = meshType;
        this.blockMeta = blockMeta;
        this.blockName = blockName;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.progress = buf.readFloat();
        this.meshType = buf.readInt();
        this.blockMeta = buf.readInt();
        this.blockName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeFloat(this.progress);
        buf.writeInt(this.meshType);
        buf.writeInt(this.blockMeta);
        ByteBufUtils.writeUTF8String(buf, this.blockName);
    }
}

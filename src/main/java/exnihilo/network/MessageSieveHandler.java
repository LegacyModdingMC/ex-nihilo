package exnihilo.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import exnihilo.blocks.tileentities.TileEntitySieve;
import exnihilo.items.meshes.MeshType;
import exnihilo.utils.BlockInfo;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class MessageSieveHandler implements IMessageHandler<MessageSieve, IMessage> {

    @Override
    public IMessage onMessage(MessageSieve message, MessageContext ctx) {
        World world = (Minecraft.getMinecraft()).thePlayer.worldObj;
        if (world.getTileEntity(message.x, message.y, message.z) != null) {
            TileEntitySieve te = (TileEntitySieve) world.getTileEntity(message.x, message.y, message.z);
            te.setMeshType(MeshType.values()[message.meshType]);
            te.setProgress(message.progress);
            Block block = (Block) Block.blockRegistry.getObject(message.blockName);
            if (block == Blocks.air) {
                te.setCurrentStack(BlockInfo.EMPTY);
            } else {
                te.setCurrentStack(new BlockInfo(block, message.blockMeta));
            }
            world.markBlockForUpdate(message.x, message.y, message.z);
        }
        return null;
    }
}

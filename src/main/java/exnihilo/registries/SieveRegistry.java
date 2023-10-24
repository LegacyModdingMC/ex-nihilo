package exnihilo.registries;

import exnihilo.ENBlocks;
import exnihilo.ENItems;
import exnihilo.ExNihilo;
import exnihilo.items.meshes.MeshType;
import exnihilo.registries.helpers.SiftingResult;
import exnihilo.utils.ItemInfo;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

public class SieveRegistry {

    public static HashMap<MeshType, HashMap<ItemInfo, ArrayList<SiftingResult>>> getSiftables() {
        return siftables;
    }

    private static final HashMap<MeshType, HashMap<ItemInfo, ArrayList<SiftingResult>>> siftables = new HashMap<>();

    static {
        for (MeshType meshType : MeshType.values()) {
            if (meshType == MeshType.NONE)
                continue;
            siftables.put(meshType, new HashMap<>());
        }
    }

    public static void register(Block source, int sourceMeta, Item output, int outputMeta, int rarity, MeshType meshType) {
        if (meshType == MeshType.NONE) return;
        if (source == null || output == null) return;
        if (rarity > 0) {
            ItemInfo iteminfo = new ItemInfo(source, sourceMeta);
            ArrayList<SiftingResult> res = siftables.get(meshType).get(iteminfo);
            if (res == null) res = new ArrayList<>();
            res.add(new SiftingResult(new ItemInfo(output, outputMeta), rarity));
            siftables.get(meshType).put(iteminfo, res);
        } else {
            ItemStack inputStack = new ItemStack(source, sourceMeta);
            ItemStack outputStack = new ItemStack(output, outputMeta);
            ExNihilo.log.info("Block "
                + inputStack.getDisplayName()
                + " with reward "
                + outputStack.getDisplayName()
                + " was not added. Reason: Chance 0");
        }
    }

    public static void register(Block source, int sourceMeta, Item output, int outputMeta, int rarity) {
        for (MeshType meshType : MeshType.values()) {
            register(source, sourceMeta, output, outputMeta, rarity, meshType);
        }
    }

    public static void register(Block source, Item output, int outputMeta, int rarity, MeshType meshType) {
        register(source, 0, output, outputMeta, rarity, meshType);
    }

    public static void register(Block source, Item output, int outputMeta, int rarity) {
        for (MeshType meshType : MeshType.values()) {
            register(source, output, outputMeta, rarity, meshType);
        }
    }

    public static ArrayList<SiftingResult> getSiftingOutput(Block block, int meta, MeshType meshType) {
        return siftables.get(meshType).get(new ItemInfo(block, meta));
    }

    public static ArrayList<SiftingResult> getSiftingOutput(ItemInfo info, MeshType meshType) {
        return siftables.get(meshType).get(info);
    }

    public static boolean registered(Block block, int meta, MeshType meshType) {
        HashMap<ItemInfo, ArrayList<SiftingResult>> res = siftables.get(meshType);
        if (res == null)
            return false;
        return res.containsKey(new ItemInfo(block, meta));
    }

    public static boolean registered(Block block, MeshType meshType) {
        HashMap<ItemInfo, ArrayList<SiftingResult>> res = siftables.get(meshType);
        if (res == null)
            return false;
        return res.containsKey(new ItemInfo(block, 32767));
    }

    public static void unregisterReward(Block block, int meta, Item output, int outputMeta, MeshType meshType) {
        ItemInfo iteminfo = new ItemInfo(block, meta);
        ArrayList<SiftingResult> res = siftables.get(meshType).get(iteminfo);
        if (res == null) return;
        res.removeIf(sr -> sr.drop.getItem() == output && sr.drop.getMeta() == outputMeta);
        if (res.isEmpty()) siftables.get(meshType).remove(iteminfo);
    }

    public static void unregisterReward(Block block, int meta, Item output, int outputMeta) {
        for (MeshType meshType : MeshType.values()) {
            if (meshType == MeshType.NONE)
                continue;
            unregisterReward(block, meta, output, outputMeta, meshType);
        }
    }

    public static void unregisterRewardFromAllBlocks(Item output, int outputMeta) {
        for (MeshType meshType : MeshType.values()) {
            if (meshType == MeshType.NONE)
                continue;
            for (ItemInfo iteminfo : siftables.get(meshType).keySet())
                unregisterReward(Block.getBlockFromItem(iteminfo.getItem()), iteminfo.getMeta(), output, outputMeta, meshType);
        }
    }

    public static void unregisterAllRewardsFromBlock(Block block, int meta) {
        for (MeshType meshType : MeshType.values()) {
            if (meshType == MeshType.NONE)
                continue;
            siftables.get(meshType).remove(new ItemInfo(block, meta));
        }
    }

    public static void load(Configuration config) {}

    public static void registerRewards() {
        register(Blocks.dirt, 0, ENItems.Stones, 0, 1);
        register(Blocks.dirt, 0, ENItems.Stones, 0, 1);
        register(Blocks.dirt, 0, ENItems.Stones, 0, 2);
        register(Blocks.dirt, 0, ENItems.Stones, 0, 2);
        register(Blocks.dirt, 0, ENItems.Stones, 0, 3);
        register(Blocks.dirt, 0, ENItems.Stones, 0, 3);
        register(Blocks.dirt, 0, Items.wheat_seeds, 0, 15);
        register(Blocks.dirt, 0, ENItems.GrassSeeds, 0, 15);
        register(Blocks.dirt, 0, Items.melon_seeds, 0, 32);
        register(Blocks.dirt, 0, Items.pumpkin_seeds, 0, 32);
        register(Blocks.dirt, 0, ENItems.SeedsSugarcane, 0, 32);
        register(Blocks.dirt, 0, ENItems.SeedsCarrot, 0, 64);
        register(Blocks.dirt, 0, ENItems.SeedsPotato, 0, 64);
        register(Blocks.dirt, 0, ENItems.SeedsOak, 0, 64);
        register(Blocks.dirt, 0, ENItems.SeedsAcacia, 0, 90);
        register(Blocks.dirt, 0, ENItems.SeedsSpruce, 0, 90);
        register(Blocks.dirt, 0, ENItems.SeedsBirch, 0, 90);
        register(Blocks.gravel, 0, Items.flint, 0, 4);
        register(Blocks.gravel, 0, Items.coal, 0, 8);
        register(Blocks.gravel, 0, Items.dye, 4, 20);
        register(Blocks.gravel, 0, Items.diamond, 0, 128);
        register(Blocks.gravel, 0, Items.emerald, 0, 150);
        register(Blocks.sand, 0, Items.dye, 3, 32);
        register(Blocks.sand, 0, ENItems.SeedsCactus, 0, 32);
        register(Blocks.sand, 0, ENItems.SeedsJungle, 0, 64);
        register(Blocks.sand, 0, ENItems.Spores, 0, 128);
        register(Blocks.soul_sand, 0, Items.quartz, 0, 1);
        register(Blocks.soul_sand, 0, Items.quartz, 0, 3);
        register(Blocks.soul_sand, 0, Items.nether_wart, 0, 20);
        register(Blocks.soul_sand, 0, Items.ghast_tear, 0, 64);
        register(ENBlocks.Dust, 0, Items.dye, 15, 5);
        register(ENBlocks.Dust, 0, Items.redstone, 0, 8);
        register(ENBlocks.Dust, 0, Items.gunpowder, 0, 15);
        register(ENBlocks.Dust, 0, Items.glowstone_dust, 0, 16);
        register(ENBlocks.Dust, 0, Items.blaze_powder, 0, 20);
    }

    public static void registerOreDictAdditions(String[] names) {
        if (names != null) for (String input : names) {
            String[] current = input.split(":");
            for (ItemStack stack : OreDictionary.getOres(current[0])) {
                Item reward = (Item) Item.itemRegistry.getObject(current[1] + ":" + current[2]);
                if (Block.getBlockFromItem(stack.getItem()) != null) register(Block.getBlockFromItem(stack.getItem()),
                    stack.getItemDamage(),
                    reward,
                    Integer.parseInt(current[3]),
                    Integer.parseInt(current[4]));
            }
        }
    }

    public static void registerNonDictAdditions(String[] names) {
        if (names != null) for (String input : names) {
            String[] current = input.split(":");
            if (current.length == 7 && Block.blockRegistry.getObject(current[0] + ":" + current[1]) != null) {
                Block source = (Block) Block.blockRegistry.getObject(current[0] + ":" + current[1]);
                Item reward = (Item) Item.itemRegistry.getObject(current[3] + ":" + current[4]);
                register(
                    source,
                    Integer.parseInt(current[2]),
                    reward,
                    Integer.parseInt(current[5]),
                    Integer.parseInt(current[6]));
            }
        }
    }

    public static HashMap<MeshType, ArrayList<ItemInfo>> getSources(ItemStack reward) {
        HashMap<MeshType, ArrayList<ItemInfo>> res = new HashMap<>();
        for (MeshType meshType : MeshType.values()) {
            if (meshType == MeshType.NONE)
                continue;
            res.put(meshType, new ArrayList<>());
            for (ItemInfo entry : siftables.get(meshType).keySet()) {
                for (SiftingResult sift : siftables.get(meshType).get(entry)) {
                    if ((new ItemInfo(sift.drop.getItem(), sift.drop.getMeta())).equals(new ItemInfo(reward))) res.get(meshType).add(entry);
                }
            }
        }
        return res;
    }
}

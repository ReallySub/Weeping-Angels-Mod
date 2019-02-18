package me.suff.angels.common;

import me.suff.angels.common.blocks.BlockAngelStatue;
import me.suff.angels.common.blocks.BlockChronodyneGenerator;
import me.suff.angels.common.blocks.BlockMineable;
import me.suff.angels.common.blocks.BlockSnowArm;
import me.suff.angels.common.entities.AngelEnums;
import me.suff.angels.common.entities.EntityWeepingAngel;
import me.suff.angels.common.items.ItemAngelSpawner;
import me.suff.angels.common.items.ItemChronodyneGenerator;
import me.suff.angels.common.items.ItemDetector;
import me.suff.angels.common.items.ItemHanging;
import me.suff.angels.common.misc.WATabs;
import me.suff.angels.common.tileentities.TileEntityChronodyneGenerator;
import me.suff.angels.common.tileentities.TileEntityPlinth;
import me.suff.angels.common.tileentities.TileEntitySnowArm;
import me.suff.angels.utils.AngelUtils;
import me.suff.angels.utils.WADamageSource;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.List;

import static me.suff.angels.WeepingAngels.MODID;

@Mod.EventBusSubscriber
public class WAObjects {
	
	public static DamageSource ANGEL = new WADamageSource("backintime"),
			STONE = new WADamageSource("punch_stone"),
			ANGEL_NECK_SNAP = new WADamageSource("neck_snap");
	
	public static List<Item> ITEMS = new ArrayList<>();
	// Helper, gets reset after init
	private static List<Item> ITEM_BLOCKS = new ArrayList<>();
	
	@SubscribeEvent
	public static void addItems(RegistryEvent.Register<Item> e) {
		IForgeRegistry<Item> reg = e.getRegistry();
		//reg.registerAll(reg, WATabs.MAIN_TAB, ITEM_BLOCKS.toArray(new Item[ITEM_BLOCKS.size()]));
		reg.registerAll(
				setUpItem(new ItemHanging(), "angel_painting", true),
				setUpItem(new ItemDetector(), "timey_wimey_detector", true),
				setUpItem(new ItemChronodyneGenerator(), "chronodyne_generator", true),
				setUpItem(new ItemAngelSpawner<>(AngelEnums.AngelType.ANGEL_ONE, EntityWeepingAngel::new), "angel_0", true),
				setUpItem(new ItemAngelSpawner<>(AngelEnums.AngelType.ANGEL_TWO, EntityWeepingAngel::new), "angel_1", true),
				setUpItem(new ItemAngelSpawner<>(AngelEnums.AngelType.ANGEL_CHILD, EntityWeepingAngel::new), "angel_child", true),
				setUpItem(new Item(new Item.Properties().group(WATabs.MAIN_TAB)), "kontron_ingot", true),
				setUpItem(new ItemAngelSpawner<>(AngelEnums.AngelType.ANGEL_THREE, EntityWeepingAngel::new), "angel_2", true),
				setUpItem(new ItemAngelSpawner<>(AngelEnums.AngelType.ANGEL_FOUR, EntityWeepingAngel::new), "angel_3", true)
		);
	}
	
	@SubscribeEvent
	public static void addBlocks(RegistryEvent.Register<Block> e) {
		IForgeRegistry<Block> reg = e.getRegistry();
		registerBlocks(reg, setUpBlock(new BlockSnowArm(), "arm"), setUpBlock(new BlockAngelStatue(), "plinth"), setUpBlock(new BlockMineable(() -> new ItemStack(Items.KONTRON_INGOT), 2, 1), "kontron_ore"));
		reg.register(setUpBlock(new BlockChronodyneGenerator(), "cg"));
	}
	
	@SubscribeEvent
	public static void regTiles(RegistryEvent.Register<TileEntityType<?>> e) {
		IForgeRegistry<TileEntityType<?>> reg = e.getRegistry();
		reg.registerAll(Tiles.ARM, Tiles.CG, Tiles.PLINTH);
	}
	
	@SubscribeEvent
	public static void addEntities(RegistryEvent.Register<EntityType<?>> e) {
		IForgeRegistry<EntityType<?>> reg = e.getRegistry();
		reg.registerAll(
				///EntityEntries.ANOMALY,
				//EntityEntries.CHRONODYNE_GENERATOR,
				EntityEntries.WEEPING_ANGEL
				//EntityEntries.WEEPING_ANGEL_PAINTING
		);
		AngelUtils.setUpSpawns();
	}
	
	@SubscribeEvent
	public static void addSounds(RegistryEvent.Register<SoundEvent> e) {
		IForgeRegistry<SoundEvent> reg = e.getRegistry();
		reg.registerAll(setUpSound("angel_seen_1"), setUpSound("angel_seen_2"), setUpSound("angel_seen_3"), setUpSound("angel_seen_4"), setUpSound("angel_seen_5"), setUpSound("angel_seen_6"), setUpSound("angel_seen_7"), setUpSound("angel_seen_8"), setUpSound("stone_scrap"), setUpSound("child_run"), setUpSound("laughing_child"), setUpSound("light_break"), setUpSound("angel_teleport"), setUpSound("angel_ambient"), setUpSound("ding"), setUpSound("blow"), setUpSound("angel_death"), setUpSound("angel_neck_snap"));
	}
	
	private static SoundEvent setUpSound(String soundName) {
		return new SoundEvent(new ResourceLocation(MODID, soundName)).setRegistryName(soundName);
	}
	
	private static Item setUpItem(Item item, String name, boolean addToTab) {
		item.setRegistryName(MODID, name);
		WAObjects.ITEMS.add(item);
		return item;
	}
	
	private static Block setUpBlock(Block block, String name) {
		block.setRegistryName(MODID, name);
		return block;
	}
	
	private static void registerBlocks(IForgeRegistry<Block> reg, Block... blocks) {
		reg.registerAll(blocks);
		for (Block block : blocks) {
			ITEM_BLOCKS.add(new ItemBlock(block, new Item.Properties().group(WATabs.MAIN_TAB)).setRegistryName(block.getRegistryName()));
		}
	}
	
	public static class Tiles {
		public static TileEntityType<?> ARM = TileEntityType.register(MODID + ":snowarm", TileEntityType.Builder.create(TileEntitySnowArm::new)).setRegistryName(new ResourceLocation(MODID + ":snowarm"));
		public static TileEntityType<?> CG = TileEntityType.register(MODID + ":cg", TileEntityType.Builder.create(TileEntityChronodyneGenerator::new)).setRegistryName(new ResourceLocation(MODID + ":cg"));
		public static TileEntityType<?> PLINTH = TileEntityType.register(MODID + ":plinth", TileEntityType.Builder.create(TileEntityPlinth::new)).setRegistryName(new ResourceLocation(MODID + ":plinth"));
	}
	
	@ObjectHolder(MODID)
	public static class Blocks {
		public static final Block ARM = null;
		public static final Block CG = null;
		public static final Block PLINTH = null;
		public static final Block KONTRON_ORE = null;
	}
	
	@ObjectHolder(MODID)
	public static class Items {
		public static final Item ANGEL_PAINTING = null;
		public static final Item TIMEY_WIMEY_DETECTOR = null;
		public static final Item CHRONODYNE_GENERATOR = null;
		public static final Item ANGEL_0 = null;
		public static final Item ANGEL_1 = null;
		public static final Item ANGEL_2 = null;
		public static final Item ANGEL_3 = null;
		public static final Item ANGEL_CHILD = null;
		public static final Item KONTRON_INGOT = null;
	}
	
	// Sounds
	@ObjectHolder(MODID)
	public static class Sounds {
		public static final SoundEvent ANGEL_SEEN_1 = null;
		public static final SoundEvent ANGEL_SEEN_2 = null;
		public static final SoundEvent ANGEL_SEEN_3 = null;
		public static final SoundEvent ANGEL_SEEN_4 = null;
		public static final SoundEvent ANGEL_SEEN_5 = null;
		public static final SoundEvent ANGEL_SEEN_6 = null;
		public static final SoundEvent ANGEL_SEEN_7 = null;
		public static final SoundEvent ANGEL_SEEN_8 = null;
		public static final SoundEvent STONE_SCRAP = null;
		public static final SoundEvent CHILD_RUN = null;
		public static final SoundEvent LAUGHING_CHILD = null;
		public static final SoundEvent LIGHT_BREAK = null;
		public static final SoundEvent ANGEL_TELEPORT = null;
		public static final SoundEvent ANGEL_AMBIENT = null;
		public static final SoundEvent DING = null;
		public static final SoundEvent BLOW = null;
		public static final SoundEvent ANGEL_DEATH = null;
		public static final SoundEvent ANGEL_NECK_SNAP = null;
	}
	
	// Entities
	@ObjectHolder(MODID)
	public static class EntityEntries {
		public static EntityType WEEPING_ANGEL = EntityType.register(MODID + ":weeping_angel", EntityType.Builder.create(EntityWeepingAngel.class, EntityWeepingAngel::new).tracker(256, 20, false)).setRegistryName(new ResourceLocation(MODID, "weeping_angel"));
		//public static final EntityEntry WEEPING_ANGEL_PAINTING = EntityEntryBuilder.create().entity(EntityAngelPainting.class).id(new ResourceLocation(MODID, "weepingAngelpainting"), 1).name("weepingAngelpainting").tracker(80, Integer.MAX_VALUE, false).build();
		//	public static final EntityEntry CHRONODYNE_GENERATOR = EntityEntryBuilder.create().entity(EntityChronodyneGenerator.class).id(new ResourceLocation(MODID, "chronodyne_generator"), 2).name("chronodyne_generator").tracker(80, 3, true).build();
		//	public static final EntityEntry ANOMALY = EntityEntryBuilder.create().entity(EntityAnomaly.class).id(new ResourceLocation(MODID, "anomaly"), 3).name("anomaly").tracker(80, 3, true).build();
	}
}
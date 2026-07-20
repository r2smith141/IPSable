package qouteall.imm_ptl.core.platform_specific;

import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.ChunkEvent;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import qouteall.imm_ptl.core.network.ImmPtlNetworkConfig;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.imm_ptl.core.portal.custom_portal_gen.PortalGenInfo;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class O_O {
    public static boolean isDimensionalThreadingPresent = false;
    
    public static boolean isForge() {
        return false;
    }
    
    //@OnlyIn(Dist.CLIENT)
    public static void onPlayerChangeDimensionClient(
        ResourceKey<Level> from, ResourceKey<Level> to
    ) {
        RequiemCompatClient.onPlayerTeleportedClient();
    }
    
    public static void onPlayerTravelOnServer(
        ServerPlayer player,
        ServerLevel fromWorld, ServerLevel toWorld
    ) {
        RequiemCompat.onPlayerTeleportedServer(player);
    }
    
    public static Path getGameDir() {
        return FMLPaths.GAMEDIR.get();
    }
    
    private static final BlockState obsidianState = Blocks.OBSIDIAN.defaultBlockState();
    
    public static boolean isObsidian(BlockState blockState) {
        return blockState == obsidianState;
    }
    
    public static void postClientChunkLoadEvent(LevelChunk chunk) {
        NeoForge.EVENT_BUS.post(new ChunkEvent.Load(chunk, true));
//        ClientChunkEvents.CHUNK_LOAD.invoker().onChunkLoad(
//            ((ClientLevel) chunk.getLevel()), chunk
//        );
    }
    
    public static void postClientChunkUnloadEvent(LevelChunk chunk) {
        NeoForge.EVENT_BUS.post(new ChunkEvent.Unload(chunk));
//        ClientChunkEvents.CHUNK_UNLOAD.invoker().onChunkUnload(
//            ((ClientLevel) chunk.getLevel()), chunk
//        );
    }
    
    public static boolean isDedicatedServer() {
        return FMLEnvironment.dist == Dist.DEDICATED_SERVER;
    }
    
    public static void postPortalSpawnEventForge(PortalGenInfo info) {
    
    }

    // @Nick1st - Moved to client class
//    @OnlyIn(Dist.CLIENT)
//    public static ClientChunkCache createMyClientChunkManager(ClientLevel world, int loadDistance) {
//        return new ImmPtlClientChunkMap(world, loadDistance);
//    }
    
    public static boolean getIsPehkuiPresent() {
        return ModList.get().isLoaded("pehkui");
    }
    
    @Nullable
    public static String getImmPtlModInfoUrl() {
        String gameVersion = SharedConstants.getCurrentVersion().getName();
        
        if (O_O.isForge()) {
            return "https://qouteall.fun/immptl_info/forge-%s.json".formatted(gameVersion);
        }
        else {
            // it's in github pages
            // https://github.com/qouteall/immptl_info
            return "https://qouteall.fun/immptl_info/%s.json".formatted(gameVersion);
        }
    }
    
    public static boolean isModLoadedWithinVersion(String modId, @Nullable String startVersion, @Nullable String endVersion) {
        Optional<? extends ModContainer> modContainer = ModList.get().getModContainerById(modId);
        if (modContainer.isPresent()) {
            ArtifactVersion version = modContainer.get().getModInfo().getVersion();
            
            try {
                if (startVersion != null) {
                    int i = new DefaultArtifactVersion(startVersion).compareTo(version);
                    if (i > 0) {
                        return false;
                    }
                }
                
                if (endVersion != null) {
                    int i = new DefaultArtifactVersion(endVersion).compareTo(version);
                    if (i < 0) {
                        return false;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            
            return true;
            
        }
        else {
            return false;
        }
    }
    
    public static @NotNull ImmPtlNetworkConfig.ModVersion getImmPtlVersion() {
        // TODO @Nick1st
//        Version version = FabricLoader.getInstance()
//            .getModContainer("iportal").orElseThrow()
//            .getMetadata().getVersion();
//
//        if (!(version instanceof SemanticVersionImpl semanticVersion)) {
//            // in dev env, its ${version}
//            return ImmPtlNetworkConfig.ModVersion.OTHER;
//        }
//
//        if (semanticVersion.getVersionComponentCount() != 3) {
//            Helper.LOGGER.error(
//                "immersive portals version {} is not in regular form", semanticVersion
//            );
//            return ImmPtlNetworkConfig.ModVersion.OTHER;
//        }
//
//        return new ImmPtlNetworkConfig.ModVersion(
//            semanticVersion.getVersionComponent(0),
//            semanticVersion.getVersionComponent(1),
//            semanticVersion.getVersionComponent(2)
//        );
        return new ImmPtlNetworkConfig.ModVersion(
                1, 0, 0
        );
    }
    
    public static String getImmPtlVersionStr() {
        // TODO @Nick1st
//        return FabricLoader.getInstance()
//            .getModContainer("iportal").orElseThrow()
//            .getMetadata().getVersion().toString();
        return "";
    }
    
    public static boolean shouldUpdateImmPtl(String latestReleaseVersion) {
        // TODO @Nick1st
//        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
//            return false;
//        }
//
//        Version currentVersion = FabricLoader.getInstance()
//            .getModContainer("iportal").get().getMetadata().getVersion();
//        try {
//            Version latestVersion = Version.parse(latestReleaseVersion);
//
//            if (latestVersion.compareTo(currentVersion) > 0) {
//                return true;
//            }
//        }
//        catch (VersionParsingException e) {
//            e.printStackTrace();
//        }
        
        return false;
    }
    
    public static String getModDownloadLink() {
        return "https://modrinth.com/mod/immersiveportals";
    }
    
    public static String getIssueLink() {
        return "https://github.com/iPortalTeam/ImmersivePortalsMod/discussions";
    }
    
    @Nullable
    public static ResourceLocation getModIconLocation(String modid) {
        // TODO @Nick1st - Fix this
        return null;
//        String path = FabricLoader.getInstance().getModContainer(modid)
//            .flatMap(c -> c.getMetadata().getIconPath(512))
//            .orElse(null);
//        if (path == null) {
//            return null;
//        }
//
//        // for example, if the icon path is "assets/modid/icon.png"
//        // then the result should be modid:icon.png
//
//        if (path.startsWith("/")) {
//            path = path.substring(1);
//        }
//        if (path.startsWith("assets")) {
//            path = path.substring("assets".length());
//        }
//        if (path.startsWith("/")) {
//            path = path.substring(1);
//        }
//        String[] parts = path.split("/");
//        if (parts.length != 2) {
//            return null;
//        }
//        return McHelper.newResourceLocation(parts[0], parts[1]);
    }
    
    @Nullable
    public static String getModName(String modid) {
        if (ModList.get().getModFileById(modid) == null) {
            return modid;
        }
        return ModList.get().getModFileById(modid).getMods().stream().findFirst().get().getDisplayName();
    }
    
    // most quilt installations use quilted fabric api
    public static boolean isQuilt() {
        return false;
        //return FabricLoader.getInstance().isModLoaded("quilted_fabric_api");
    }
    
    public static List<String> getLoadedModIds() {
        return ModList.get().getSortedMods().stream().map(ModContainer::getModId).sorted().toList();
    }

    public static boolean allowTeleportingEntity(Entity entity, Portal portal) {
        // ForgeHooks.onTravelToDimension() on Forge
        return true;
    }
    
    public static boolean isDevEnv() {
        return !FMLEnvironment.production;
    }
}

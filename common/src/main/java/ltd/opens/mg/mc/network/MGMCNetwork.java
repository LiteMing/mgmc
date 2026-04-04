package ltd.opens.mg.mc.network;

import dev.architectury.networking.NetworkManager;
import ltd.opens.mg.mc.network.payloads.*;
import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class MGMCNetwork {

    public static void init() {
        // Client -> Server
        NetworkManager.registerReceiver(NetworkManager.c2s(), RequestBlueprintListPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Server.handleRequestList(RequestBlueprintListPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.c2s(), RequestBlueprintDataPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Server.handleRequestData(RequestBlueprintDataPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.c2s(), SaveBlueprintPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Server.handleSave(SaveBlueprintPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.c2s(), DeleteBlueprintPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Server.handleDelete(DeleteBlueprintPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.c2s(), RenameBlueprintPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Server.handleRename(RenameBlueprintPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.c2s(), DuplicateBlueprintPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Server.handleDuplicate(DuplicateBlueprintPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.c2s(), RequestMappingsPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Server.handleRequestMappings(RequestMappingsPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.c2s(), SaveMappingsPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Server.handleSaveMappings(SaveMappingsPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.c2s(), WorkbenchActionPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Server.handleWorkbenchAction(WorkbenchActionPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.c2s(), RequestExportPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Server.handleRequestExport(RequestExportPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.c2s(), ImportBlueprintPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Server.handleImport(ImportBlueprintPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.c2s(), ClientActionResponsePayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Server.handleClientActionResponse(ClientActionResponsePayload.decode(buf), ctx));

        // Server -> Client
        NetworkManager.registerReceiver(NetworkManager.s2c(), ResponseBlueprintListPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Client.handleResponseList(ResponseBlueprintListPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.s2c(), ResponseBlueprintDataPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Client.handleResponseData(ResponseBlueprintDataPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.s2c(), SaveResultPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Client.handleSaveResult(SaveResultPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.s2c(), ResponseMappingsPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Client.handleResponseMappings(ResponseMappingsPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.s2c(), ResponseExportPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Client.handleResponseExport(ResponseExportPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.s2c(), RuntimeErrorReportPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Client.handleRuntimeError(RuntimeErrorReportPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.s2c(), ExecuteClientActionPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Client.handleExecuteClientAction(ExecuteClientActionPayload.decode(buf), ctx));
        NetworkManager.registerReceiver(NetworkManager.s2c(), WorkbenchScriptsPayload.ID,
            (buf, ctx) -> BlueprintNetworkHandler.Client.handleWorkbenchScripts(WorkbenchScriptsPayload.decode(buf), ctx));
    }

    public static void sendToPlayer(ServerPlayer player, ResponseBlueprintListPayload payload) {
        NetworkManager.sendToPlayer(player, ResponseBlueprintListPayload.ID, encodePayload(payload));
    }

    public static void sendToPlayer(ServerPlayer player, ResponseBlueprintDataPayload payload) {
        NetworkManager.sendToPlayer(player, ResponseBlueprintDataPayload.ID, encodePayload(payload));
    }

    public static void sendToPlayer(ServerPlayer player, SaveResultPayload payload) {
        NetworkManager.sendToPlayer(player, SaveResultPayload.ID, encodePayload(payload));
    }

    public static void sendToPlayer(ServerPlayer player, ResponseMappingsPayload payload) {
        NetworkManager.sendToPlayer(player, ResponseMappingsPayload.ID, encodePayload(payload));
    }

    public static void sendToPlayer(ServerPlayer player, ResponseExportPayload payload) {
        NetworkManager.sendToPlayer(player, ResponseExportPayload.ID, encodePayload(payload));
    }

    public static void sendToPlayer(ServerPlayer player, RuntimeErrorReportPayload payload) {
        NetworkManager.sendToPlayer(player, RuntimeErrorReportPayload.ID, encodePayload(payload));
    }

    public static void sendToPlayer(ServerPlayer player, ExecuteClientActionPayload payload) {
        NetworkManager.sendToPlayer(player, ExecuteClientActionPayload.ID, encodePayload(payload));
    }

    public static void sendToPlayer(ServerPlayer player, WorkbenchScriptsPayload payload) {
        NetworkManager.sendToPlayer(player, WorkbenchScriptsPayload.ID, encodePayload(payload));
    }

    // C2S sends
    public static void sendToServer(RequestBlueprintListPayload payload) {
        NetworkManager.sendToServer(RequestBlueprintListPayload.ID, encodePayload(payload));
    }

    public static void sendToServer(RequestBlueprintDataPayload payload) {
        NetworkManager.sendToServer(RequestBlueprintDataPayload.ID, encodePayload(payload));
    }

    public static void sendToServer(SaveBlueprintPayload payload) {
        NetworkManager.sendToServer(SaveBlueprintPayload.ID, encodePayload(payload));
    }

    public static void sendToServer(DeleteBlueprintPayload payload) {
        NetworkManager.sendToServer(DeleteBlueprintPayload.ID, encodePayload(payload));
    }

    public static void sendToServer(RenameBlueprintPayload payload) {
        NetworkManager.sendToServer(RenameBlueprintPayload.ID, encodePayload(payload));
    }

    public static void sendToServer(DuplicateBlueprintPayload payload) {
        NetworkManager.sendToServer(DuplicateBlueprintPayload.ID, encodePayload(payload));
    }

    public static void sendToServer(RequestMappingsPayload payload) {
        NetworkManager.sendToServer(RequestMappingsPayload.ID, encodePayload(payload));
    }

    public static void sendToServer(SaveMappingsPayload payload) {
        NetworkManager.sendToServer(SaveMappingsPayload.ID, encodePayload(payload));
    }

    public static void sendToServer(WorkbenchActionPayload payload) {
        NetworkManager.sendToServer(WorkbenchActionPayload.ID, encodePayload(payload));
    }

    public static void sendToServer(RequestExportPayload payload) {
        NetworkManager.sendToServer(RequestExportPayload.ID, encodePayload(payload));
    }

    public static void sendToServer(ImportBlueprintPayload payload) {
        NetworkManager.sendToServer(ImportBlueprintPayload.ID, encodePayload(payload));
    }

    public static void sendToServer(ClientActionResponsePayload payload) {
        NetworkManager.sendToServer(ClientActionResponsePayload.ID, encodePayload(payload));
    }

    @SuppressWarnings("unchecked")
    private static <T> FriendlyByteBuf encodePayload(T payload) {
        FriendlyByteBuf buf = buf();
        // Use the static encode method on the payload class via a helper
        // We need to use the encode methods defined on each payload
        if (payload instanceof ResponseBlueprintListPayload p) return encode(p);
        if (payload instanceof ResponseBlueprintDataPayload p) return encode(p);
        if (payload instanceof SaveResultPayload p) return encode(p);
        if (payload instanceof ResponseMappingsPayload p) return encode(p);
        if (payload instanceof ResponseExportPayload p) return encode(p);
        if (payload instanceof RuntimeErrorReportPayload p) return encode(p);
        if (payload instanceof ExecuteClientActionPayload p) return encode(p);
        if (payload instanceof WorkbenchScriptsPayload p) return encode(p);
        if (payload instanceof RequestBlueprintListPayload p) return encode(p);
        if (payload instanceof RequestBlueprintDataPayload p) return encode(p);
        if (payload instanceof SaveBlueprintPayload p) return encode(p);
        if (payload instanceof DeleteBlueprintPayload p) return encode(p);
        if (payload instanceof RenameBlueprintPayload p) return encode(p);
        if (payload instanceof DuplicateBlueprintPayload p) return encode(p);
        if (payload instanceof RequestMappingsPayload p) return encode(p);
        if (payload instanceof SaveMappingsPayload p) return encode(p);
        if (payload instanceof WorkbenchActionPayload p) return encode(p);
        if (payload instanceof RequestExportPayload p) return encode(p);
        if (payload instanceof ImportBlueprintPayload p) return encode(p);
        if (payload instanceof ClientActionResponsePayload p) return encode(p);
        throw new IllegalArgumentException("Unknown payload type: " + payload.getClass());
    }

    private static FriendlyByteBuf buf() { return new FriendlyByteBuf(Unpooled.buffer()); }

    private static FriendlyByteBuf encode(ResponseBlueprintListPayload p) { FriendlyByteBuf b = buf(); ResponseBlueprintListPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(ResponseBlueprintDataPayload p) { FriendlyByteBuf b = buf(); ResponseBlueprintDataPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(SaveResultPayload p) { FriendlyByteBuf b = buf(); SaveResultPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(ResponseMappingsPayload p) { FriendlyByteBuf b = buf(); ResponseMappingsPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(ResponseExportPayload p) { FriendlyByteBuf b = buf(); ResponseExportPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(RuntimeErrorReportPayload p) { FriendlyByteBuf b = buf(); RuntimeErrorReportPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(ExecuteClientActionPayload p) { FriendlyByteBuf b = buf(); ExecuteClientActionPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(WorkbenchScriptsPayload p) { FriendlyByteBuf b = buf(); WorkbenchScriptsPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(RequestBlueprintListPayload p) { FriendlyByteBuf b = buf(); RequestBlueprintListPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(RequestBlueprintDataPayload p) { FriendlyByteBuf b = buf(); RequestBlueprintDataPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(SaveBlueprintPayload p) { FriendlyByteBuf b = buf(); SaveBlueprintPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(DeleteBlueprintPayload p) { FriendlyByteBuf b = buf(); DeleteBlueprintPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(RenameBlueprintPayload p) { FriendlyByteBuf b = buf(); RenameBlueprintPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(DuplicateBlueprintPayload p) { FriendlyByteBuf b = buf(); DuplicateBlueprintPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(RequestMappingsPayload p) { FriendlyByteBuf b = buf(); RequestMappingsPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(SaveMappingsPayload p) { FriendlyByteBuf b = buf(); SaveMappingsPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(WorkbenchActionPayload p) { FriendlyByteBuf b = buf(); WorkbenchActionPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(RequestExportPayload p) { FriendlyByteBuf b = buf(); RequestExportPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(ImportBlueprintPayload p) { FriendlyByteBuf b = buf(); ImportBlueprintPayload.encode(b, p); return b; }
    private static FriendlyByteBuf encode(ClientActionResponsePayload p) { FriendlyByteBuf b = buf(); ClientActionResponsePayload.encode(b, p); return b; }
}

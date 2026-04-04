package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ExecuteClientActionPayload(String blueprintName, String nodeId, String actionType, String data) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "execute_client_action");

    public static ExecuteClientActionPayload decode(FriendlyByteBuf buf) {
        return new ExecuteClientActionPayload(buf.readUtf(), buf.readUtf(), buf.readUtf(), buf.readUtf());
    }

    public static void encode(FriendlyByteBuf buf, ExecuteClientActionPayload payload) {
        buf.writeUtf(payload.blueprintName());
        buf.writeUtf(payload.nodeId());
        buf.writeUtf(payload.actionType());
        buf.writeUtf(payload.data());
    }
}

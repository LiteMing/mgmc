package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ClientActionResponsePayload(String blueprintName, String nodeId, String data) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "client_action_response");

    public static ClientActionResponsePayload decode(FriendlyByteBuf buf) {
        return new ClientActionResponsePayload(buf.readUtf(), buf.readUtf(), buf.readUtf());
    }

    public static void encode(FriendlyByteBuf buf, ClientActionResponsePayload payload) {
        buf.writeUtf(payload.blueprintName());
        buf.writeUtf(payload.nodeId());
        buf.writeUtf(payload.data());
    }
}

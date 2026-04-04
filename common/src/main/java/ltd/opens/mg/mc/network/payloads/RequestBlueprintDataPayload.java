package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record RequestBlueprintDataPayload(String name) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "request_blueprint_data");

    public static RequestBlueprintDataPayload decode(FriendlyByteBuf buf) {
        return new RequestBlueprintDataPayload(buf.readUtf());
    }

    public static void encode(FriendlyByteBuf buf, RequestBlueprintDataPayload payload) {
        buf.writeUtf(payload.name());
    }
}

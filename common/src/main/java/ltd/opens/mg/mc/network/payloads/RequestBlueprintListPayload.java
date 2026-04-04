package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record RequestBlueprintListPayload() {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "request_blueprint_list");

    public static RequestBlueprintListPayload decode(FriendlyByteBuf buf) {
        return new RequestBlueprintListPayload();
    }

    public static void encode(FriendlyByteBuf buf, RequestBlueprintListPayload payload) {
    }
}

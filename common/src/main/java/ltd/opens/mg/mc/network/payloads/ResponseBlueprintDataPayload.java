package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ResponseBlueprintDataPayload(String name, String data, long version) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "response_blueprint_data");

    public static ResponseBlueprintDataPayload decode(FriendlyByteBuf buf) {
        return new ResponseBlueprintDataPayload(buf.readUtf(), buf.readUtf(1048576), buf.readVarLong());
    }

    public static void encode(FriendlyByteBuf buf, ResponseBlueprintDataPayload payload) {
        buf.writeUtf(payload.name());
        buf.writeUtf(payload.data(), 1048576);
        buf.writeVarLong(payload.version());
    }
}

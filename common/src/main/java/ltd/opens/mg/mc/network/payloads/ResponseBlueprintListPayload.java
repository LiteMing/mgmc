package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public record ResponseBlueprintListPayload(List<String> blueprints) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "response_blueprint_list");

    public static ResponseBlueprintListPayload decode(FriendlyByteBuf buf) {
        List<String> list = buf.readCollection(ArrayList::new, FriendlyByteBuf::readUtf);
        return new ResponseBlueprintListPayload(list);
    }

    public static void encode(FriendlyByteBuf buf, ResponseBlueprintListPayload payload) {
        buf.writeCollection(payload.blueprints(), FriendlyByteBuf::writeUtf);
    }
}

package ltd.opens.mg.mc.network.payloads;

import ltd.opens.mg.mc.MaingraphforMC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record WorkbenchActionPayload(Action action, String blueprintPath) {
    public static final ResourceLocation ID = new ResourceLocation(MaingraphforMC.MODID, "workbench_action");

    public enum Action {
        BIND,
        UNBIND
    }

    public static WorkbenchActionPayload decode(FriendlyByteBuf buf) {
        Action action = Action.values()[buf.readVarInt()];
        String path = buf.readUtf();
        return new WorkbenchActionPayload(action, path);
    }

    public static void encode(FriendlyByteBuf buf, WorkbenchActionPayload payload) {
        buf.writeVarInt(payload.action().ordinal());
        buf.writeUtf(payload.blueprintPath());
    }
}

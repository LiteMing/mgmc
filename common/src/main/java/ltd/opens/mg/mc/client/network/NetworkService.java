package ltd.opens.mg.mc.client.network;

import ltd.opens.mg.mc.network.MGMCNetwork;
import ltd.opens.mg.mc.network.payloads.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 独立网络服务层，解耦 UI 与网络协议。
 * UI 应调用此处的业务接口，而非直接构造和发送网络包。
 */
public class NetworkService {
    private static final NetworkService INSTANCE = new NetworkService();

    private NetworkService() {}

    public static NetworkService getInstance() {
        return INSTANCE;
    }

    public void saveBlueprint(String name, String json, long version) {
        MGMCNetwork.sendToServer(new SaveBlueprintPayload(name, json, version));
    }

    public void requestBlueprintList() {
        MGMCNetwork.sendToServer(new RequestBlueprintListPayload());
    }

    public void deleteBlueprint(String name) {
        MGMCNetwork.sendToServer(new DeleteBlueprintPayload(name));
    }

    public void renameBlueprint(String oldName, String newName) {
        MGMCNetwork.sendToServer(new RenameBlueprintPayload(oldName, newName));
    }

    public void duplicateBlueprint(String sourceName, String targetName) {
        MGMCNetwork.sendToServer(new DuplicateBlueprintPayload(sourceName, targetName));
    }

    public void requestMappings() {
        MGMCNetwork.sendToServer(new RequestMappingsPayload());
    }

    public void saveMappings(Map<String, Set<String>> mappings) {
        MGMCNetwork.sendToServer(new SaveMappingsPayload(new HashMap<>(mappings)));
    }

    public void requestBlueprintData(String name) {
        MGMCNetwork.sendToServer(new RequestBlueprintDataPayload(name));
    }

    public void sendWorkbenchAction(WorkbenchActionPayload.Action action, String path) {
        MGMCNetwork.sendToServer(new WorkbenchActionPayload(action, path));
    }

    public void requestExport(String name) {
        MGMCNetwork.sendToServer(new RequestExportPayload(name));
    }

    public void importBlueprint(String name, String data, Map<String, Set<String>> mappings) {
        MGMCNetwork.sendToServer(new ImportBlueprintPayload(name, data, mappings));
    }
}

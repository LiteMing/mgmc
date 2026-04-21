# Maingraph for Minecraft (MGMC) Makefile
# 快速启动与构建脚本

GRADLEW = ./gradlew

.PHONY: fabric forge neoforge run-fabric run-forge run-neoforge clean build help

# 默认显示帮助
all: help

# 运行 Fabric 客户端
fabric: run-fabric
run-fabric:
	$(GRADLEW) :fabric:runClient

# 运行 Forge 客户端
forge: run-forge
run-forge:
	$(GRADLEW) :forge:runClient

# 兼容旧命令
neoforge: run-forge
run-neoforge: run-forge

# 清理项目
clean:
	$(GRADLEW) clean

# 构建项目
build:
	$(GRADLEW) build

# 帮助信息
help:
	@echo "MGMC 快速启动脚本"
	@echo "用法:"
	@echo "  make fabric      - 启动 Fabric 客户端"
	@echo "  make forge       - 启动 Forge 客户端"
	@echo "  make neoforge    - 兼容旧命令，实际启动 Forge 客户端"
	@echo "  make build       - 构建所有平台的 jar 包"
	@echo "  make clean       - 清理构建缓存"
	@echo "  make help        - 显示此帮助信息"

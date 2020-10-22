package datagen;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Generator {
    private static final String DEFAULT_BLOCKSTATE = "{\n" +
                                                     "  \"variants\": {\n" +
                                                     "    \"\": {\n" +
                                                     "      \"model\": \"endless_fabric:block/%s\"\n" +
                                                     "    }\n" +
                                                     "  }\n" +
                                                     "}\n";
    private static final String DEFAULT_BLOCK_MODEL = "{\n" +
                                                      "  \"parent\": \"block/cube_all\",\n" +
                                                      "  \"textures\": {\n" +
                                                      "    \"all\": \"endless_fabric:block/%s\"\n" +
                                                      "  }\n" +
                                                      "}\n";
    private static final String DEFAULT_BLOCKITEM_MODEL = "{\n" + "  \"parent\": \"endless_fabric:block/%s\"\n" + "}\n";
    private static final String DEFAULT_BLOCK_LOOT_TABLE = "{\n" +
                                                           "  \"type\": \"minecraft:block\",\n" +
                                                           "  \"pools\": [\n" +
                                                           "    {\n" +
                                                           "      \"rolls\": 1,\n" +
                                                           "      \"entries\": [\n" +
                                                           "        {\n" +
                                                           "          \"type\": \"minecraft:item\",\n" +
                                                           "          \"name\": \"endless_fabric:%s\"\n" +
                                                           "        }\n" +
                                                           "      ],\n" +
                                                           "      \"functions\": [\n" +
                                                           "        {\n" +
                                                           "          \"function\": \"set_count\",\n" +
                                                           "          \"count\": 1\n" +
                                                           "        }\n" +
                                                           "      ],\n" +
                                                           "      \"conditions\": [\n" +
                                                           "        {\n" +
                                                           "          \"condition\": \"minecraft:survives_explosion\"\n" +
                                                           "        }\n" +
                                                           "      ]\n" +
                                                           "    }\n" +
                                                           "  ]\n" +
                                                           "}\n";

    // IMPORTANT
    // Make sure the working directory is the project root folder
    // Ensure that it is not set the 'run' directory
    public static void main(String[] args) {
        try (Scanner s = new Scanner(System.in)) {
            System.out.println("Enter the name of the block");
            String name = s.next();
            String blockstate = String.format(DEFAULT_BLOCKSTATE, name);
            String blockModel = String.format(DEFAULT_BLOCK_MODEL, name);
            String blockitemModel = String.format(DEFAULT_BLOCKITEM_MODEL, name);
            String lootTable = String.format(DEFAULT_BLOCK_LOOT_TABLE, name);
            Path root = Paths.get(System.getProperty("user.dir"));
            Path assetsDir = root.resolve("src").resolve("main").resolve("resources").resolve("assets").resolve("endless_fabric");
            Path blockstatePath = assetsDir.resolve("blockstates").resolve(name + ".json");
            Path blockModelPath = assetsDir.resolve("models").resolve("block").resolve(name + ".json");
            Path blockitemModelPath = assetsDir.resolve("models").resolve("item").resolve(name + ".json");
            Path lootTablePath = root.resolve("src").resolve("main").resolve("resources").resolve("data").resolve("endless_fabric").resolve("loot_tables").resolve("blocks").resolve(name + ".json");
            Files.write(blockstatePath)
        }
    }
}
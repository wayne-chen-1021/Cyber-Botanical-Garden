package com.cyberbotanic.data;

import java.util.Map;
import java.util.HashMap;

public class PlantVisuals {

    public static class PlantStageVisual {
        public final String stage;     // 例如 "seed", "sprout", "grown", "blooming", "bloomed"
        public final String asciiArt;

        public PlantStageVisual(String stage, String asciiArt) {
            this.stage = stage;
            this.asciiArt = asciiArt;
        }
    }

    public static class PotVisual {
        public final String name;
        public final String asciiArt;

        public PotVisual(String name, String asciiArt) {
            this.name = name;
            this.asciiArt = asciiArt;
        }
    }

    // key: cactus, rose...
    public static final Map<String, Map<String, PlantStageVisual>> PLANTS = new HashMap<>();

    // 可切換不同盆栽
    public static final Map<String, PotVisual> POTS = new HashMap<>();

    static {
        // ========== 無種物 ==========
        Map<String, PlantStageVisual> noneStages = new HashMap<>();
        noneStages.put("none", new PlantStageVisual("none", """
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss"""));
        // ========== 測試植物 ==========
        Map<String, PlantStageVisual> budStages = new HashMap<>();
        // 種子
        budStages.put("seed", new PlantStageVisual("seed", """
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            sssssssssssssssssOOOOOOsssssssssssssssss"""));
        // 發芽
        budStages.put("sprout", new PlantStageVisual("sprout", """
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            sssssssssssssdbssssssssssssMbsssssssssss
            sssssssssssdMMMMssssssssMMMMMbssssssssss
            ssssssssssssdMMMMMssssMMMMbsssssssssssss
            sssssssssssssssdMMMMMMMbssssssssssssssss
            sssssssssssssssssssMM*ssssssssssssssssss
            ssssssssssssssssss.MMsssssssssssssssssss"""));
        // 成長
        budStages.put("grown", new PlantStageVisual("grown", """
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            ssssssssssssssssssssssssssssssssssssssss
            sssssssssssssssssssTTsssssssssssssssssss
            Sssssssssssssssssssdbsssssssssssssssssss
            sssssssssss.dMMMb.sdbss.dMMMb.ssssssssss
            ssssssssssssssdMMMMMMMMMMMMbssssssssssss
            ssssssssssssssssssdMMMMMbsssssssssssssss
            ssssssssssssssssssssMMssssssssssssssssss
            sssssss.dMMMb.ssssssMMssssssdMMMb.ssssss
            ssssssssssdMMMMMbssMMMsssdMMMMbsssssssss
            Ssssssssssss*MMMMMMMMMMMMMMbssssssssssss
            Ssssssssssssssssss.MMM.sssssssssssssssss
            ssssssssssssssssss.MMM.sssssssssssssssss"""));
        // 開花
        budStages.put("blooming", new PlantStageVisual("blooming", """
            sssssssssssssssssMMMMMMsssssssssssssssss
            ssssssssssssssssMMMMMMMMssssssssssssssss
            sssssssssssssssssMMMMMMsssssssssssssssss
            sssssssssssssssssssMMsssssssssssssssssss
            sssssssssssssssssssMMsssssssssssssssssss
            SssssssssssssssssssMMsssssssssssssssssss
            sssssssss..odMMMb.sMMMs.dMMMbo..ssssssss
            ssssssssssssssdMMMMMMMMMMMMbssssssssssss
            ssssssssssssssssssdMMMMMbsssssssssssssss
            sssssssssssssssssssMMMssssssssssssssssss
            sssssss..odMMMMMbssMMMsssdMMMMbo..ssssss
            Ssssssssssss*MMMMMMMMMMMMMMbsssssSssssss
            Ssssssssssssssssss.MMM.sssssssssssssssss
            ssssssssssssssssss.MMM.sssssssssssssssss"""));


        // 各成長階段加入對應植物圖片顯示
        PLANTS.put("none", noneStages);
        PLANTS.put("bud", budStages);

        // ========== 盆栽樣式 ==========
        POTS.put("default", new PotVisual("標準盆栽", """
            ssssss.|========================|.ssssss
            ssssss.|MMMMMMMMMMMMMMMMMMMMMMMM|.ssssss
            ssssss.*|MMMMMMMMMMMMMMMMMMMMMM|*.ssssss
            sssssss.\\MMMMMMMMMMMMMMMMMMMMMM/.sssssss
            ssssssss.\\MMMMMMMMMMMMMMMMMMMM/.ssssssss
            sssssssss.\\BBBBBBBBBBBBBBBBBB/.sssssssss
            """));
    }

    // 輸出組合圖（植物 + 盆栽）
    public static String render(String type, String stage, String potStyle) {
        PlantStageVisual plant = PLANTS.getOrDefault(type, Map.of())
                                       .getOrDefault(stage, new PlantStageVisual(stage, "(?)"));
        PotVisual pot = POTS.getOrDefault(potStyle, new PotVisual("default", "(無盆栽)"));
        return plant.asciiArt + "\n" + pot.asciiArt;
    }
}

class PlantVisualsTest {
    public static void main(String[] args) {
        String type = "bud";
        String stage = "blooming";
        String potStyle = "default";

        String visual = PlantVisuals.render(type, stage, potStyle);
        System.out.println(visual);
    }
}

/* 顏色對照
|   顏色      ANSI Code   
| --------  ------------ 
| **重設**   `\u001B[0m ` 
| **黑色**   `\u001B[30m` 
| **紅色**   `\u001B[31m` 
| **綠色**   `\u001B[32m` 
| **黃色**   `\u001B[33m` 
| **藍色**   `\u001B[34m` 
| **紫色**   `\u001B[35m` 
| **青色**   `\u001B[36m` 
| **白色**   `\u001B[37m` 
 */
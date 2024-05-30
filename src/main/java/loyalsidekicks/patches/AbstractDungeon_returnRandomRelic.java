package loyalsidekicks.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import loyalsidekicks.LoyalSidekicks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@SpirePatch2(
        clz = AbstractDungeon.class,
        method = "returnRandomRelic"
)
public class AbstractDungeon_returnRandomRelic {
    private static final ArrayList<String> loyalRelics1 = new ArrayList<>();
    private static final ArrayList<String> loyalRelics2 = new ArrayList<>();
    private static final ArrayList<String> loyalRelics3 = new ArrayList<>();

    public static SpireReturn<AbstractRelic> Prefix() {
        try {
            SpireConfig config = new SpireConfig("LoyalSidekicks", "LoyalSidekicksConfig");
            config.load();
            if (config.getBool("modEnabled") && AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite && AbstractDungeon.getCurrRoom().monsters.getMonsterNames().contains("SpireSpear")) {
                initializeLoyalRelics();
                String relicID;
                if (!loyalRelics1.isEmpty()) {
                    relicID = loyalRelics1.get(AbstractDungeon.relicRng.random(loyalRelics1.size() - 1));
                } else if (!loyalRelics2.isEmpty()) {
                    relicID = loyalRelics2.get(AbstractDungeon.relicRng.random(loyalRelics2.size() - 1));
                } else {
                    relicID = loyalRelics3.get(AbstractDungeon.relicRng.random(loyalRelics3.size() - 1));
                }
                AbstractDungeon.commonRelicPool.removeIf(e -> Objects.equals(e, relicID));
                AbstractDungeon.uncommonRelicPool.removeIf(e -> Objects.equals(e, relicID));
                AbstractDungeon.rareRelicPool.removeIf(e -> Objects.equals(e, relicID));
                AbstractDungeon.shopRelicPool.removeIf(e -> Objects.equals(e, relicID));
                AbstractDungeon.bossRelicPool.removeIf(e -> Objects.equals(e, relicID));
                return SpireReturn.Return(RelicLibrary.getRelic(relicID).makeCopy());
            }
        } catch (IOException e) {
            LoyalSidekicks.logger.error("LoyalSidekicks | Failed to load config data!");
        }
        return SpireReturn.Continue();
    }

    private static void initializeLoyalRelics() {
        // Loyal Relics Tier 1.
        if (AbstractDungeon.uncommonRelicPool.contains("Eternal Feather")) {
            loyalRelics1.add("Eternal Feather"); // 永恒羽毛
        }
        if (AbstractDungeon.uncommonRelicPool.contains("Gremlin Horn")) {
            loyalRelics1.add("Gremlin Horn"); // 地精之角
        }
        if (AbstractDungeon.uncommonRelicPool.contains("White Beast Statue")) {
            loyalRelics1.add("White Beast Statue"); // 白兽雕像
        }
        if (AbstractDungeon.rareRelicPool.contains("The Specimen")) {
            loyalRelics1.add("The Specimen"); // 生物样本
        }
        if (AbstractDungeon.uncommonRelicPool.contains("Blue Candle") && !CardHelper.hasCardType(AbstractCard.CardType.CURSE)) {
            loyalRelics1.add("Blue Candle"); // 蓝蜡烛
        }
        if (AbstractDungeon.commonRelicPool.contains("Blood Vial") && (AbstractDungeon.player.hasRelic("Mark of the Bloom") || AbstractDungeon.player.currentHealth == AbstractDungeon.player.maxHealth)) {
            loyalRelics1.add("Blood Vial"); // 小血瓶
        }
        if (AbstractDungeon.uncommonRelicPool.contains("Pantograph") && (AbstractDungeon.player.hasRelic("Mark of the Bloom") || AbstractDungeon.player.currentHealth == AbstractDungeon.player.maxHealth)) {
            loyalRelics1.add("Pantograph"); // 缩放仪
        }
        if (AbstractDungeon.commonRelicPool.contains("Toy Ornithopter") && (AbstractDungeon.player.hasRelic("Mark of the Bloom") || (AbstractDungeon.player.hasRelic("Sozu") && !AbstractDungeon.player.hasAnyPotions()))) {
            loyalRelics1.add("Toy Ornithopter"); // 玩具扑翼飞机
        }
        if (AbstractDungeon.commonRelicPool.contains("Strawberry") && AbstractDungeon.player.hasRelic("Mark of the Bloom")) {
            loyalRelics1.add("Strawberry"); // 草莓
        }
        if (AbstractDungeon.uncommonRelicPool.contains("Pear") && AbstractDungeon.player.hasRelic("Mark of the Bloom")) {
            loyalRelics1.add("Pear"); // 梨子
        }
        if (AbstractDungeon.rareRelicPool.contains("Bird Faced Urn") && AbstractDungeon.player.hasRelic("Mark of the Bloom")) {
            loyalRelics1.add("Bird Faced Urn"); // 鸟面瓮
        }
        if (AbstractDungeon.rareRelicPool.contains("Lizard Tail") && AbstractDungeon.player.hasRelic("Mark of the Bloom")) {
            loyalRelics1.add("Lizard Tail"); // 蜥蜴尾巴
        }
        if (AbstractDungeon.rareRelicPool.contains("Magic Flower") && AbstractDungeon.player.hasRelic("Mark of the Bloom")) {
            loyalRelics1.add("Magic Flower"); // 魔法花
        }
        if (AbstractDungeon.rareRelicPool.contains("Mango") && AbstractDungeon.player.hasRelic("Mark of the Bloom")) {
            loyalRelics1.add("Mango"); // 芒果
        }

        // Loyal Relics Tier 2.
        if (AbstractDungeon.commonRelicPool.contains("Ancient Tea Set")) {
            loyalRelics2.add("Ancient Tea Set"); // 古茶具套装
        }
        if (AbstractDungeon.commonRelicPool.contains("CeramicFish")) {
            loyalRelics2.add("CeramicFish"); // 陶瓷小鱼
        }
        if (AbstractDungeon.commonRelicPool.contains("Dream Catcher")) {
            loyalRelics2.add("Dream Catcher"); // 捕梦网
        }
        if (AbstractDungeon.commonRelicPool.contains("Juzu Bracelet")) {
            loyalRelics2.add("Juzu Bracelet"); // 佛珠手链
        }
        if (AbstractDungeon.commonRelicPool.contains("MawBank")) {
            loyalRelics2.add("MawBank"); // 巨口储蓄罐
        }
        if (AbstractDungeon.commonRelicPool.contains("MealTicket")) {
            loyalRelics2.add("MealTicket"); // 餐券
        }
        if (AbstractDungeon.commonRelicPool.contains("Omamori")) {
            loyalRelics2.add("Omamori"); // 御守
        }
        if (AbstractDungeon.commonRelicPool.contains("PreservedInsect")) {
            loyalRelics2.add("PreservedInsect"); // 昆虫标本
        }
        if (AbstractDungeon.commonRelicPool.contains("Regal Pillow")) {
            loyalRelics2.add("Regal Pillow"); // 皇家枕头
        }
        if (AbstractDungeon.commonRelicPool.contains("Smiling Mask")) {
            loyalRelics2.add("Smiling Mask"); // 微笑面具
        }
        if (AbstractDungeon.commonRelicPool.contains("Tiny Chest")) {
            loyalRelics2.add("Tiny Chest"); // 小宝箱
        }
        if (AbstractDungeon.commonRelicPool.contains("Potion Belt") && AbstractDungeon.player.hasRelic("Sozu")) {
            loyalRelics2.add("Potion Belt"); // 药水腰带
        }

        if (AbstractDungeon.uncommonRelicPool.contains("Darkstone Periapt")) {
            loyalRelics2.add("Darkstone Periapt"); // 黑石护符
        }
        if (AbstractDungeon.uncommonRelicPool.contains("Matryoshka")) {
            loyalRelics2.add("Matryoshka"); // 套娃
        }
        if (AbstractDungeon.uncommonRelicPool.contains("Meat on the Bone")) {
            loyalRelics2.add("Meat on the Bone"); // 带骨肉
        }
        if (AbstractDungeon.uncommonRelicPool.contains("Question Card")) {
            loyalRelics2.add("Question Card"); // 问号牌
        }
        if (AbstractDungeon.uncommonRelicPool.contains("The Courier")) {
            loyalRelics2.add("The Courier"); // 送货员
        }
        if (AbstractDungeon.uncommonRelicPool.contains("Singing Bowl") && AbstractDungeon.player.hasRelic("Mark of the Bloom")) {
            loyalRelics2.add("Singing Bowl"); // 颂钵
        }

        if (AbstractDungeon.rareRelicPool.contains("Girya")) {
            loyalRelics2.add("Girya"); // 壶铃
        }
        if (AbstractDungeon.rareRelicPool.contains("Old Coin")) {
            loyalRelics2.add("Old Coin"); // 古钱币
        }
        if (AbstractDungeon.rareRelicPool.contains("Peace Pipe")) {
            loyalRelics2.add("Peace Pipe"); // 宁静烟斗
        }
        if (AbstractDungeon.rareRelicPool.contains("Prayer Wheel")) {
            loyalRelics2.add("Prayer Wheel"); // 转经轮
        }
        if (AbstractDungeon.rareRelicPool.contains("Shovel")) {
            loyalRelics2.add("Shovel"); // 铲子
        }
        if (AbstractDungeon.rareRelicPool.contains("WingedGreaves")) {
            loyalRelics2.add("WingedGreaves"); // 羽翼之靴
        }

        // Loyal Relics Tier 3.
        loyalRelics3.add("CultistMask"); // 邪教徒头套
        loyalRelics3.add("FaceOfCleric"); // 牧师的脸
        loyalRelics3.add("NlothsMask"); // 恩洛斯的饥饿的脸
        loyalRelics3.add("Spirit Poop"); // 精灵便便
        loyalRelics3.add("SsserpentHead"); // 蛇的头
        /*
        loyalRelics3.add("Golden Idol"); // 金神像
        if (AbstractDungeon.bossRelicPool.contains("Black Blood")) {
            loyalRelics3.add("Black Blood"); // 黑暗之血
        }
        if (AbstractDungeon.bossRelicPool.contains("Black Star")) {
            loyalRelics3.add("Black Star"); // 黑星
        }
        if (AbstractDungeon.bossRelicPool.contains("SacredBark") && AbstractDungeon.player.hasRelic("Sozu") && !AbstractDungeon.player.hasAnyPotions()) {
            loyalRelics3.add("SacredBark"); // 神圣树皮
        }
        if (AbstractDungeon.shopRelicPool.contains("Cauldron") && AbstractDungeon.player.hasRelic("Sozu")) {
            loyalRelics3.add("Cauldron"); // 大锅
        }
        if (AbstractDungeon.shopRelicPool.contains("Lee's Waffle") && AbstractDungeon.player.hasRelic("Mark of the Bloom")) {
            loyalRelics3.add("Lee's Waffle"); // 李家华夫饼
        }
        if (AbstractDungeon.shopRelicPool.contains("Membership Card")) {
            loyalRelics3.add("Membership Card"); // 会员卡
        }
        if (AbstractDungeon.shopRelicPool.contains("HandDrill")) {
            loyalRelics3.add("HandDrill"); // 手钻
        }
        if (AbstractDungeon.shopRelicPool.contains("Sling")) {
            loyalRelics3.add("Sling"); // 勇气投石索
        }
        loyalRelics3.add("Circlet"); // 头环
        */
    }
}

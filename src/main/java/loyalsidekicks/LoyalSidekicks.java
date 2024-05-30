package loyalsidekicks;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import loyalsidekicks.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@SpireInitializer
public class LoyalSidekicks implements PostInitializeSubscriber, EditStringsSubscriber {
    public static final Logger logger = LogManager.getLogger(LoyalSidekicks.class.getName());
    public static boolean modEnabled = true;

    public LoyalSidekicks() {
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        new LoyalSidekicks();
    }

    @Override
    public void receivePostInitialize() {
        loadData();

        initializeConfigPanel();
    }

    @Override
    public void receiveEditStrings() {
        loadLocalizationFiles(Settings.language);
    }

    private void saveData() {
        logger.info("LoyalSidekicks | Saving config data...");
        try {
            SpireConfig config = new SpireConfig("LoyalSidekicks", "LoyalSidekicksConfig");
            config.setBool("modEnabled", modEnabled);
            config.save();
        } catch (IOException e) {
            logger.error("LoyalSidekicks | Failed to save config data!");
        }
    }

    private void loadData() {
        logger.info("LoyalSidekicks | Loading config data...");
        try {
            SpireConfig config = new SpireConfig("LoyalSidekicks", "LoyalSidekicksConfig");
            config.load();
            if (config.has("modEnabled")) {
                modEnabled = config.getBool("modEnabled");
            } else {
                clearData();
            }
        } catch (IOException e) {
            logger.error("LoyalSidekicks | Failed to load config data!");
            clearData();
        }
    }

    private void clearData() {
        logger.info("LoyalSidekicks | Clearing config data...");
        modEnabled = true;
        saveData();
    }

    private void initializeConfigPanel() {
        logger.info("LoyalSidekicks | Initializing config panel...");

        ModPanel configPanel = new ModPanel();
        BaseMod.registerModBadge(TextureLoader.getTexture("loyalsidekicks/images/ui/Badge.png"), "LoyalSidekicks", "瓶装易碎柯妮法", "Loyal Sidekicks : 忠心耿耿矛与盾\n\nEverything is the same but Spire Shield and Spire Spear will only drop relics that prove their loyalty to the Corrupt Heart.\n什么都没变，但是矛与盾掉落的遗物将会证明他们对腐化之心的忠诚～", configPanel);

        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("loyalsidekicks:Config");

        ModLabeledToggleButton toggleButton1 = new ModLabeledToggleButton(uiStrings.TEXT[0], 400f, 700f, Settings.CREAM_COLOR, FontHelper.buttonLabelFont, modEnabled, configPanel,
                (me) -> {
                    // Label update function.
                },
                (me) -> {
                    // Toggle function.
                    modEnabled = me.enabled;
                    saveData();
                });
        toggleButton1.toggle.enabled = modEnabled;

        configPanel.addUIElement(toggleButton1);
    }

    private void loadLocalizationFiles(Settings.GameLanguage language) {
        if (language == Settings.GameLanguage.ZHS) {
            BaseMod.loadCustomStringsFile(UIStrings.class, "loyalsidekicks/localization/zhs/ui.json");
        } else {
            BaseMod.loadCustomStringsFile(UIStrings.class, "loyalsidekicks/localization/eng/ui.json");
        }
    }
}

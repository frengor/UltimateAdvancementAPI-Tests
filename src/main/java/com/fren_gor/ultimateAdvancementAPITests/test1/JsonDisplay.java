package com.fren_gor.ultimateAdvancementAPITests.test1;

import com.fren_gor.ultimateAdvancementAPI.advancement.display.AdvancementDisplay;
import com.fren_gor.ultimateAdvancementAPI.nms.wrappers.advancement.PreparedAdvancementDisplayWrapper;
import com.fren_gor.ultimateAdvancementAPI.nms.util.JsonString;
import com.fren_gor.ultimateAdvancementAPI.util.LazyValue;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class JsonDisplay extends AdvancementDisplay {
    @LazyValue
    private PreparedAdvancementDisplayWrapper wrapper;

    public JsonDisplay(@NotNull AdvancementDisplay display) {
        super(
                display.getIcon(),
                display.getTitle(),
                display.getDescription(),
                display.getDefaultTitleStyle(),
                display.getAnnouncementMessageDefaultTitleStyle(),
                display.getDefaultDescriptionStyle(),
                display.getAnnouncementMessageDefaultDescriptionStyle(),
                display.getFrame(),
                display.doesShowToast(),
                display.doesAnnounceToChat(),
                display.getX(),
                display.getY()
        );
    }

    @Override
    @NotNull
    public PreparedAdvancementDisplayWrapper getNMSWrapper() throws ReflectiveOperationException {
        if (wrapper != null) {
            return wrapper;
        }

        var old = super.getNMSWrapper();
        String title = ComponentSerializer.toString(old.getTitle());
        String desc = ComponentSerializer.toString(old.getDescription());
        return wrapper = PreparedAdvancementDisplayWrapper.craft(getIcon(), new JsonString(title), new JsonString(desc), getFrame().getNMSWrapper(), getX(), getY());
    }
}

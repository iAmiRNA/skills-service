package skills.service.datastore.services.settings;

import skills.service.controller.request.model.SettingsRequest;
import skills.service.datastore.services.settings.listeners.ValidationRes;
import skills.storage.model.Setting;

public interface SettingChangedListener {
    boolean supports(SettingsRequest setting);
    void execute(Setting previousValue, SettingsRequest newValue);
    ValidationRes isValid(SettingsRequest newValue);
}

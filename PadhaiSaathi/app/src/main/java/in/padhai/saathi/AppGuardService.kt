package in.padhai.saathi

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.content.Intent

/*
 * सुरक्षा नोट:
 * यह केवल सेवा का शुरुआती ढाँचा है। किसी दूसरे ऐप को विश्वसनीय रूप से रोकने के लिए
 * अभिभावक द्वारा चुनी गई ऐप सूची, समय-सारणी, लॉक स्क्रीन और बायपास परीक्षण जोड़ना बाकी है।
 * Accessibility अनुमति बहुत शक्तिशाली होती है; इसे केवल स्पष्ट अभिभावक सहमति से चालू करें।
 */
class AppGuardService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // MVP scaffold: अभी कोई ऐप अपने-आप ब्लॉक नहीं किया जाता।
        // अगली अवस्था में माता-पिता द्वारा चुने गए पैकेज और सक्रिय अध्ययन-सत्र जाँचे जाएँगे।
    }
    override fun onInterrupt() {}
}

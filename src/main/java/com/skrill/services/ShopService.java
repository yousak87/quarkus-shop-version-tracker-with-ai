package com.skrill.services;

import com.skrill.config.FreeMarkerConfigProvider;
import com.skrill.model.Shop;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ShopService {

    @ConfigProperty(name = "email.address.receiver.primary")
    String emailAddressReceiver;

    @ConfigProperty(name = "email.address.receiver.cc")
    String emailAddressReceiverCc;

    @Inject
    GeminiService geminiService;

    @Inject
    JsoupService jsoupService;

    @Inject
    FreeMarkerConfigProvider freemarkerConfig;

    private static final Logger log = LoggerFactory.getLogger(ShopService.class);

    @Inject
    Mailer mailer;

    @Transactional
    public void updateShopVersion() {

        List<Shop> shops = Shop.listAll();

        for (Shop shop : shops) {
            try {
                LocalDate today = LocalDate.now();
                if (shop.last_update.toLocalDate().isBefore(today) && shop.running_state == 0) {

                    String htmlContent = jsoupService.getWebContent(shop.url, shop.repo_type);

//                    String latestVersion = "";
                    String latestVersion = geminiService.chat("Extract the newest version number from this HTML. Return ONLY the version in format like these examples: v1.2.3, 5.5.0-rc.3, 2.0.1-beta. No other text. HTML: '" + htmlContent + "'");
                    latestVersion = latestVersion.replaceAll("[\\n\\t\\r]", "");
                    log.info("latest version from AI for {} {}",shop.name, latestVersion);
                    if (shop.latest_version == null) {
                        shop.latest_version = latestVersion;
                        shop.last_update = LocalDateTime.now();

                        sendEmail(shop);
                    } else if (!shop.latest_version.equalsIgnoreCase(latestVersion)) {
                        shop.latest_version = latestVersion;
                        shop.last_update = LocalDateTime.now();
                        sendEmail(shop);
                    } else {
                        shop.last_update = LocalDateTime.now();
                        log.info("no new plugin version for {} , latest version is {} and latest updated on {}", shop.name, shop.latest_version, shop.last_update);
                    }

                }
            } catch (Exception e) {
                log.error("failed to get version of {}", shop.name);
                log.error(e.getMessage());
            }

            Shop.persist(shops);

        }
    }

    @Transactional
    public void setShopFlagToOne() {

        List<Shop> shops = Shop.listAll();

        for (Shop shop : shops) {
            if (shop.last_run != null) {
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(shop.last_run)) {
                    Shop.update("running_state=?1", 0);
                    log.info("Today's date is greater than last_run.");
                } else {
                    log.info("Today's date is not greater than last_run. no need to run again");
                }
            } else {
                Shop.update("running_state=?1", 0);
                log.info("update state to 0.");
            }
        }
    }

    public void sendEmail(Shop shop) throws IOException, TemplateException {
        Map<String, Object> templateData = new HashMap<String, Object>();
        templateData.put("shop", shop); // shop adalah object dengan property name dan url

        Configuration cfg = freemarkerConfig.getFreeMarkerConfig();
        StringWriter contentWriter = new StringWriter();

        String subject = new StringWriter() {{
            cfg.getTemplate("newPluginSubject.ftl")
                    .process(templateData, this);
        }}.toString();

        String content = new StringWriter() {{
            cfg.getTemplate("newPluginContent.ftl")
                    .process(templateData, this);
        }}.toString();

        String [] receiverCC= (emailAddressReceiverCc).split(",");
        mailer.send(Mail.withHtml(emailAddressReceiver, subject, content).addCc(receiverCC));


    }

}

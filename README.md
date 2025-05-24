# Shop System Version Tracker (Quarkus + AI)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Quarkus](https://img.shields.io/badge/3.15.3+-blue.svg)](https://quarkus.io)

Automated solution to track latest software versions for e-commerce platforms by combining:
- Web crawling (GitLab/GitHub)
- AI-powered version detection (Gemini)
- Smart notification system

## Key Features 🔍

1. **AI-Powered Version Detection**  
   - Gemini AI analyzes HTML/APIs to identify true latest versions
   - Handles ambiguous version formats (v1.2.3, release-2024, etc.)

2. **Smart Database Storage**  
   - Automatically stores new versions in database (PostgreSQL/MySQL)
   - Only updates when version changes detected
   - Maintains historical version data

3. **Email Notifications**  
   - Instant alerts when new versions are detected
   - Configurable email templates
   - Supports multiple recipients

4. **Multi-Platform Support**  
   - WooCommerce, Magento, Shopify, PrestaShop + more

## Why This Exists

E-commerce platforms and their plugins often lag behind updates, creating security and compatibility risks. This tool solves:

### For Developers
🔧 **Stay Ahead of Updates**  
- Proactively track new platform versions before they impact your plugins  
- Get early warnings for breaking changes  

### For Security Teams
🛡️ **Vulnerability Prevention**  
- Identify outdated installations vulnerable to exploits  
- Maintain PCI-DSS/compliance requirements  

### For Merchants
🛒 **Business Continuity**  
- Avoid unexpected compatibility issues after updates  
- Plan upgrades during low-traffic periods  

### For Plugin Developers
⚙️ **Compatibility Intelligence**  
- Automated alerts when new platform versions affect your plugins  
- Historical data to test against multiple platform versions  
- Reduce "why doesn't this work?" support tickets  

## How It Works
1. Crawls project repositories
2. Extracts release data via API/HTML
3. Gemini AI parses and validates versions
4. Stores results in database (if new/updated)
5. Sends email notifications on changes

## Run the Appliaction
### Prerequisites
- Java 17+
- Docker (optional)
- [Gemini API Key](https://ai.google.dev/)
- SMTP credentials for email

### 1. Clone & Configure
```bash
git clone https://github.com/yousak87/quarkus-shop-version-tracker-with-ai.git
cd quarkus-shop-version-tracker-with-ai
```
### 2. Create Table Shop and Configure the App Db
```bash
- create database then import sql query 'skrill_crawler.sql' under src\main\resources
- configure your database inside application.properties
```

### 3. Notification Configuration
Set up in `application.properties`:
```properties
# Email settings
quarkus.mailer.auth-methods=DIGEST-MD5 CRAM-SHA256 CRAM-SHA1 CRAM-MD5 PLAIN LOGIN
quarkus.mailer.from=your_email_account
quarkus.mailer.host=smtp.gmail.com
quarkus.mailer.port=587
quarkus.mailer.start-tls=REQUIRED
quarkus.mailer.username=your_email_account
quarkus.mailer.password=email_app_password
quarkus.mailer.mock=false # In dev mode, prevent from using the mock SMTP server
email.address.receiver.primary=mail_address1,mail_address2
email.address.receiver.cc=mail_cc_address1,mail_cc_address2
```

### 4. AI Configuration
Set up in `application.properties`:
```properties
# AI settings
google.ai.gemini.api-key=gemini_api_key
google.ai.gemini.model=gemini-2.0-flash
google.ai.gemini.temperature=0.7
```

### 5. run application
to Run application on local:
```properties
mvn quarkus:dev
```
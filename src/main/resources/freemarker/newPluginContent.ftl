<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Compatibility Testing: ${shop.name} v${shop.latest_version}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.4;
            color: #333333;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 100%;
            max-width: 600px;
            margin: 0 auto;
        }
        .header {
            background-color: #d97706;
            color: #ffffff;
            padding: 20px;
            text-align: center;
        }
        .content {
            padding: 20px;
            background-color: #ffffff;
        }
        .version-badge {
            display: inline-block;
            background-color: #fef3c7;
            color: #92400e;
            padding: 5px 10px;
            border-radius: 4px;
            font-weight: bold;
            margin: 5px 0;
        }
        .button {
            background-color: #2563eb;
            color: #ffffff !important;
            text-decoration: none;
            padding: 10px 20px;
            display: inline-block;
            margin: 15px 0;
        }
        .footer {
            padding: 15px;
            text-align: center;
            font-size: 12px;
            color: #666666;
            background-color: #f8fafc;
        }
    </style>
</head>
<body>
    <!--[if mso]>
    <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
    <tr>
    <td>
    <![endif]-->
    <div class="container">
        <!-- Header with Outlook fix -->
        <table width="100%" cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td bgcolor="#d97706" class="header">
                    <h1 style="margin:0;color:#ffffff;">Compatibility Testing Required</h1>
                </td>
            </tr>
        </table>

        <div class="content">
            <p>Dear Esphere Team,</p>

            <p>A new version of <strong>${shop.name}</strong> has been released:</p>

            <div class="version-badge">Version ${shop.latest_version}</div>

            <p>Please verify your plugins for:</p>
            <ul>
                <li>API endpoints compatibility</li>
                <li>Authentication flows</li>
                <li>Data format changes</li>
                <li>UI integration</li>
            </ul>

            <!-- Outlook-friendly button -->
            <table cellpadding="0" cellspacing="0" border="0">
                <tr>
                    <td bgcolor="#2563eb" style="padding: 10px 20px; border-radius: 4px;">
                        <a href="${shop.url}" style="color: #ffffff; text-decoration: none; display: inline-block;">
                            Shop Repo URL
                        </a>
                    </td>
                </tr>
            </table>
        </div>

        <div class="footer">
            <p>© ${.now?string('yyyy')} Esphere Developer Network</p>
            <p>This is an automated message</p>
        </div>
    </div>
    <!--[if mso]>
    </td>
    </tr>
    </table>
    <![endif]-->
</body>
</html>
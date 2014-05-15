package net.unknownmc.antiadvertiser;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AntiAdvertiser extends JavaPlugin {
    // Based on http://data.iana.org/TLD/tlds-alpha-by-domain.txt, retrieved 2014-05-15 14:42 UTC
    public static String TLDregex = "AC|ACADEMY|ACCOUNTANTS|ACTOR|AD|AE|AERO|AF|AG|AGENCY|AI|AIRFORCE|AL|AM|AN|AO|AQ|AR|ARCHI|ARPA|AS|ASIA|ASSOCIATES|AT|AU|AW|AX|AXA|AZ|BA|BAR|BARGAINS|BAYERN|BB|BD|BE|BERLIN|BEST|BF|BG|BH|BI|BID|BIKE|BIZ|BJ|BLACK|BLACKFRIDAY|BLUE|BM|BN|BO|BOUTIQUE|BR|BS|BT|BUILD|BUILDERS|BUZZ|BV|BW|BY|BZ|CA|CAB|CAMERA|CAMP|CAPITAL|CARDS|CARE|CAREER|CAREERS|CASH|CAT|CATERING|CC|CD|CENTER|CEO|CF|CG|CH|CHEAP|CHRISTMAS|CI|CITIC|CK|CL|CLAIMS|CLEANING|CLINIC|CLOTHING|CLUB|CM|CN|CO|CODES|COFFEE|COLLEGE|COLOGNE|COM|COMMUNITY|COMPANY|COMPUTER|CONDOS|CONSTRUCTION|CONSULTING|CONTRACTORS|COOKING|COOL|COOP|COUNTRY|CR|CREDIT|CREDITCARD|CRUISES|CU|CV|CW|CX|CY|CZ|DANCE|DATING|DE|DEMOCRAT|DENTAL|DESI|DIAMONDS|DIGITAL|DIRECTORY|DISCOUNT|DJ|DK|DM|DNP|DO|DOMAINS|DZ|EC|EDU|EDUCATION|EE|EG|EMAIL|ENGINEERING|ENTERPRISES|EQUIPMENT|ER|ES|ESTATE|ET|EU|EUS|EVENTS|EXCHANGE|EXPERT|EXPOSED|FAIL|FARM|FEEDBACK|FI|FINANCE|FINANCIAL|FISH|FISHING|FITNESS|FJ|FK|FLIGHTS|FLORIST|FM|FO|FOO|FOUNDATION|FR|FROGANS|FUND|FURNITURE|FUTBOL|GA|GAL|GALLERY|GB|GD|GE|GF|GG|GH|GI|GIFT|GL|GLASS|GLOBO|GM|GMO|GN|GOP|GOV|GP|GQ|GR|GRAPHICS|GRATIS|GRIPE|GS|GT|GU|GUITARS|GURU|GW|GY|HAUS|HK|HM|HN|HOLDINGS|HOLIDAY|HORSE|HOUSE|HR|HT|HU|ID|IE|IL|IM|IMMOBILIEN|IN|INDUSTRIES|INFO|INK|INSTITUTE|INSURE|INT|INTERNATIONAL|INVESTMENTS|IO|IQ|IR|IS|IT|JE|JETZT|JM|JO|JOBS|JP|KAUFEN|KE|KG|KH|KI|KIM|KITCHEN|KIWI|KM|KN|KOELN|KP|KR|KRED|KW|KY|KZ|LA|LAND|LB|LC|LEASE|LI|LIGHTING|LIMITED|LIMO|LINK|LK|LONDON|LR|LS|LT|LU|LUXURY|LV|LY|MA|MAISON|MANAGEMENT|MANGO|MARKETING|MC|MD|ME|MEDIA|MEET|MENU|MG|MH|MIAMI|MIL|MK|ML|MM|MN|MO|MOBI|MODA|MOE|MONASH|MOSCOW|MP|MQ|MR|MS|MT|MU|MUSEUM|MV|MW|MX|MY|MZ|NA|NAGOYA|NAME|NC|NE|NET|NEUSTAR|NF|NG|NI|NINJA|NL|NO|NP|NR|NU|NYC|NZ|OKINAWA|OM|ONL|ORG|PA|PARIS|PARTNERS|PARTS|PE|PF|PG|PH|PHOTO|PHOTOGRAPHY|PHOTOS|PICS|PICTURES|PINK|PK|PL|PLUMBING|PM|PN|POST|PR|PRO|PRODUCTIONS|PROPERTIES|PS|PT|PUB|PW|PY|QA|QPON|QUEBEC|RE|RECIPES|RED|REISEN|REN|RENTALS|REPAIR|REPORT|REST|REVIEWS|RICH|RO|ROCKS|RODEO|RS|RU|RUHR|RW|RYUKYU|SA|SAARLAND|SB|SC|SCHULE|SD|SE|SERVICES|SEXY|SG|SH|SHIKSHA|SHOES|SI|SINGLES|SJ|SK|SL|SM|SN|SO|SOCIAL|SOHU|SOLAR|SOLUTIONS|SOY|SR|ST|SU|SUPPLIES|SUPPLY|SUPPORT|SURGERY|SV|SX|SY|SYSTEMS|SZ|TATTOO|TAX|TC|TD|TECHNOLOGY|TEL|TF|TG|TH|TIENDA|TIPS|TJ|TK|TL|TM|TN|TO|TODAY|TOKYO|TOOLS|TOWN|TOYS|TP|TR|TRADE|TRAINING|TRAVEL|TT|TV|TW|TZ|UA|UG|UK|UNIVERSITY|UNO|US|UY|UZ|VA|VACATIONS|VC|VE|VEGAS|VENTURES|VG|VI|VIAJES|VILLAS|VISION|VN|VODKA|VOTE|VOTING|VOTO|VOYAGE|VU|WANG|WATCH|WEBCAM|WED|WF|WIEN|WIKI|WORKS|WS|WTC|WTF|XN--3BST00M|XN--3DS443G|XN--3E0B707E|XN--45BRJ9C|XN--55QW42G|XN--55QX5D|XN--6FRZ82G|XN--6QQ986B3XL|XN--80ADXHKS|XN--80AO21A|XN--80ASEHDB|XN--80ASWG|XN--90A3AC|XN--C1AVG|XN--CG4BKI|XN--CLCHC0EA0B2G2A9GCD|XN--CZRU2D|XN--D1ACJ3B|XN--FIQ228C5HS|XN--FIQ64B|XN--FIQS8S|XN--FIQZ9S|XN--FPCRJ9C3D|XN--FZC2C9E2C|XN--GECRJ9C|XN--H2BRJ9C|XN--I1B6B1A6A2E|XN--IO0A7I|XN--J1AMH|XN--J6W193G|XN--KPRW13D|XN--KPRY57D|XN--L1ACC|XN--LGBBAT1AD8J|XN--MGB9AWBF|XN--MGBA3A4F16A|XN--MGBAAM7A8H|XN--MGBAB2BD|XN--MGBAYH7GPA|XN--MGBBH1A71E|XN--MGBC0A9AZCG|XN--MGBERP4A5D4AR|XN--MGBX4CD0AB|XN--NGBC5AZD|XN--NQV7F|XN--NQV7FS00EMA|XN--O3CW4H|XN--OGBPF8FL|XN--P1AI|XN--PGBS0DH|XN--Q9JYB4C|XN--RHQV96G|XN--S9BRJ9C|XN--SES554G|XN--UNUP4Y|XN--WGBH1C|XN--WGBL6A|XN--XKC2AL3HYE2A|XN--XKC2DL3A5EE0H|XN--YFRO4I67O|XN--YGBI2AMMX|XN--ZFR164B|XXX|XYZ|YE|YOKOHAMA|YT|ZA|ZM|ZONE|ZW";
    public static FileConfiguration config;
    public static File detectionsFile;

    public void onEnable() {
        getServer().getPluginManager().registerEvents(new AdvertiseListener(), this);
        getDataFolder().mkdirs();
        detectionsFile = new File(getDataFolder(), "detections.txt");
        try {
            if (!detectionsFile.exists()) {
                detectionsFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        config = getConfig();
        config.options().copyDefaults(true).copyHeader(true);
        saveDefaultConfig();
        if ((!config.getString("onDetect.action").equalsIgnoreCase("WARN")) && (config.getString("onDetect.action").equalsIgnoreCase("KICK"))) {
            getLogger().info("Value of onDetect.action is either empty of invalid (" + config.getString("onDetect.action") + "), defaulting to 'WARN'.");
            config.set("onDetect.action", "WARN");
            saveConfig();
        }
    }

    public void onDisable() {

    }

    public static boolean checkForIp(String str) {
        if (!config.getBoolean("checks.ips")) {
            return false;
        }
        String ipPattern = "([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})";
        Pattern r = Pattern.compile(ipPattern);
        Matcher m = r.matcher(str);
        if (m.find()) {
            sendDebug("The received message contained an IP.");
            return true;
        }
        sendDebug("The received message did NOT contain an IP.");
        return false;
    }

    public static boolean checkForDomain(String str) {
        if (!config.getBoolean("checks.domains")) {
            return false;
        }
        List<String> domainPatterns = new ArrayList();
        domainPatterns.add("(?i)([a-zA-Z0-9]{1,50}\\.(" + TLDregex + "):[0-9]{2,5})"); // mc.unknownmc.net:25565
        domainPatterns.add("(?i)([a-zA-Z0-9]{1,50}\\.(" + TLDregex + ")\\/)"); // www.unknownmc.net/
        domainPatterns.add("(?i)([a-zA-Z0-9]{1,50}\\.(" + TLDregex + ") )"); // mc.unknownmc.net rocks
        domainPatterns.add("(?i)([a-zA-Z0-9]{1,50}\\.(" + TLDregex + "))$"); // "Join mc.unknownmc.net"
        domainPatterns.add("(?i)([a-zA-Z0-9]{1,50}\\.(" + TLDregex + ")(\\!|\\.|\\?))"); // Join mc.unknownmc.net!
        for (String regex : domainPatterns) {
            Pattern r = Pattern.compile(regex);
            Matcher m = r.matcher(str);
            if (m.find()) {
                sendDebug("The received message contained a website. Matched regex '" + regex + "'");
                return true;
            }
        }
        sendDebug("The received message did NOT contain a website.");
        return false;
    }

    public static String checkForWhitelist(String str) {
        String finish = str;
        for (Iterator localIterator = config.getList("whitelist").iterator(); localIterator.hasNext(); ) { Object l = localIterator.next();
            Object needle = l;
            finish = finish.toLowerCase().replaceAll(needle.toString().toLowerCase(), "");
        }
        sendDebug("Checked for whitelist, " + finish);
        return finish;
    }

    public static boolean checkForAbsoluteWhitelist(String str) {
        for (String absolute : config.getStringList("absolute-whitelist")) {
            if (str.contains(absolute.toLowerCase())) {
                sendDebug("Message contained an absolute-whitelist string " + absolute);
                return true;
            }
        }
        return false;
    }

    public static boolean checkForBlacklist(String str) {
        if (!config.getBoolean("checks.blacklist")) {
            return false;
        }
        for (String blacklist : config.getStringList("blacklist")) {
            if (blacklist.startsWith("regex:")) {
                Pattern p = Pattern.compile(blacklist.substring(7));
                Matcher m = p.matcher(str);
                if (m.find()) {
                    sendDebug("Message contained blacklisted Regular Expression " + blacklist);
                    return true;
                }
                return false;
            }
            if (str.contains(blacklist.toLowerCase())) {
                sendDebug("Message contained blacklisted phrase " + blacklist);
                return true;
            }
            sendDebug(str + " does not contain " + blacklist);
        }
        return false;
    }

    public static boolean safeChat(Player player, String message) {
        message = message.toLowerCase();
        message = message.replace("\n", "");
        String whitelist = checkForWhitelist(message);
        if (checkForAbsoluteWhitelist(message)) {
            sendDebug("Message is on absolute whitelist");
            return true;
        }
        if (!player.hasPermission("antiadvertiser.bypass.blacklist")) {
            if (checkForBlacklist(message)) {
                sendDebug("Message contains blacklisted message.");
                return false;
            }
            sendDebug("Blacklist not found");
        }
        if (!whitelist.equals(message)) {
            sendDebug("Message is partially whitelisted, changing " + message + " to " + whitelist);
            message = whitelist;
        }
        if (!player.hasPermission("antiadvertiser.bypass.ip")) {
            if (checkForIp(message)) {
                sendDebug("Message contains IP");
                return false;
            }
            sendDebug("IP not found");
        }
        if (!player.hasPermission("antiadvertiser.bypass.domain")) {
            if (checkForDomain(message)) {
                sendDebug("Message contains domain name");
                return false;
            }
            sendDebug("Domain not found");
        }

        sendDebug("Message is good.");
        return true;
    }

    public static void sendDebug(String message) {
        if (config.getBoolean("debug")) {
            Bukkit.getConsoleSender().sendMessage("[Debug] " + message);
        }
    }
}
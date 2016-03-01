package dev.util;

import com.google.common.collect.Maps;
import com.mongodb.client.MongoCollection;
import dev.IceWars;
import org.bson.Document;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

public class Ranks {
    private static final Map<Player, String> GROUP = Maps.newHashMap();

    public static String getGroup(Player p) {

        if (GROUP.containsKey(p)) {
            return GROUP.get(p);
        }

        UUID uuid = IceWars.getUUID(p);
        MongoCollection<Document> collection = MongoConnection.getCollection("perms", "users_in_groups");

        String group = collection.find(eq("uuid", uuid.toString())).first().getString("group");
        GROUP.put(p, group);

        return group;
    }

    public static String getPrefix(Player p) {
        String group = getGroup(p).toLowerCase();
        String prefix = "§7";
        if (group.equalsIgnoreCase("admin")) {
            prefix = "§4[Admin] §7";
        } else if (group.equalsIgnoreCase("developer")) {
            prefix = "§b[Dev] §7";
        } else if (group.equalsIgnoreCase("moderator")) {
            prefix = "§e[Mod] §7";
        } else if (group.equalsIgnoreCase("vip")) {
            prefix = "§5[VIP] §7";
        } else if (group.equalsIgnoreCase("premium")) {
            prefix = "§6[Premium] §7";
        }
        return prefix;
    }

    public static String getColor(Player p) {
        String group = getGroup(p).toLowerCase();
        String color = "§7";
        if (group.equalsIgnoreCase("admin")) {
            color = "§4";
        } else if (group.equalsIgnoreCase("developer")) {
            color = "§b";
        } else if (group.equalsIgnoreCase("moderator")) {
            color = "§e";
        } else if (group.equalsIgnoreCase("vip")) {
            color = "§5";
        } else if (group.equalsIgnoreCase("premium")) {
            color = "§6";
        }
        return color;
    }

}

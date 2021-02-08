package me.caledonian.hyskiespunch.managers;

import me.caledonian.hyskiespunch.HyskiesPunch;
import me.caledonian.hyskiespunch.utils.Utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceholderHandler extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "hyskiespunch";
    }

    @Override
    public String getAuthor() {
        return "Caledonian";
    }

    @Override
    public String getVersion() {
        return "0.1";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if (p == null) {
            return "ERROR: Null";
        }
        if (params.equalsIgnoreCase("canpunch")) {
            return Utils.binary(HyskiesPunch.data.canPunch(p));
        }else if(params.equalsIgnoreCase("canbepunched")){
            return Utils.binary(HyskiesPunch.data.canBePunched(p));
        }else if(params.equalsIgnoreCase("canpunch_raw")){
            return String.valueOf(HyskiesPunch.data.canPunch(p));
        }else if(params.equalsIgnoreCase("canbepunched_raw")){
            return String.valueOf(HyskiesPunch.data.canBePunched(p));
        }
        return null;

    }
}

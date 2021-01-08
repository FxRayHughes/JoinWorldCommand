package ray.mintcat.joinworldcommand

import io.izzel.taboolib.TabooLibAPI
import io.izzel.taboolib.module.inject.TListener
import io.izzel.taboolib.util.Features
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerChangedWorldEvent

@TListener
class JoinWorldListener : Listener {

    @EventHandler
    fun onPlayerJoinServer(event: PlayerChangedWorldEvent) {
        val player = event.player
        val config = JoinWorldCommand.settings.getStringList("worlds.${event.player.world.name}.commands")
        val commandGroup = TabooLibAPI.getPluginBridge().setPlaceholders(player, config) ?: return
        for (i in commandGroup) {
            val value = i.split(": ")
            when (value[0]) {
                "player" -> Features.dispatchCommand(player, value[1])
                "op" -> Features.dispatchCommand(player, value[1], true)
                "console" -> Features.dispatchCommand(Bukkit.getConsoleSender(), value[1])
            }
        }
    }

}
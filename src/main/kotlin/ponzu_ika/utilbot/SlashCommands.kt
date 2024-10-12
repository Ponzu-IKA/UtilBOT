package ponzu_ika.utilbot

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class SlashCommands :ListenerAdapter(){

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when(event.name) {
            "oc" -> {
                event.replyEmbeds(
                    GregTechEnergy().calc(
                        event.getOption("voltage")!!.asInt,
                        event.getOption("time")!!.asDouble,
                        if (event.getOption("istick") == null) false else event.getOption("istick")!!.asBoolean
                    )
                ).queue()
            }
            "forge" -> {
                val hammerValue = event.getOption("hammer")!!.asInt
                val sequence =  event.getOption("sequence")!!.asString.split(",").map(String::toInt)
                event.reply(
                    TFCForge().hammerCalc(
                        hammerValue,
                        sequence,
                        event.messageChannel
                    )
                ).queue()
            }
            "laser" -> {
                //Tierが一つ上がるに連れ10倍になる
                val ce = 0.4
                val blueCount = event.getOption("blue")?.asInt ?: 0
                val greenCount = event.getOption("green")?.asInt ?: 0
                val redCount = event.getOption("red")?.asInt ?: 0
                val whiteCount = event.getOption("white")?.asInt ?: 0

                val energy = LaserEnergy().calc(blueCount + whiteCount*3, greenCount + whiteCount*3, redCount + whiteCount*3)



                val blueCE = blueCount*ce
                val greenCE = greenCount*ce*10
                val redCE = redCount*ce*100
                val whiteCE = whiteCount*ce*1000

                val cePerTick = blueCE + redCE + greenCE + whiteCE

                val efficiency = energy/cePerTick

                event.reply("青レーザー本数: $blueCount \n緑レーザー本数: $greenCount \n赤レーザー本数: $redCount \n白レーザー本数: $whiteCount \n総エネルギー量: ${Util().round(energy,4)}\n消費エネルギー: ${Util().round(cePerTick,2)} CE\\t\n変換効率: ${Util().round(efficiency,4)}").queue()
            }
        }
    }
}
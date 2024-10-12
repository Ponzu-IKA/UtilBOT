package ponzu_ika.utilbot

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color
import kotlin.math.max

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

                val allRGBLaserCount = blueCount + greenCount + redCount

                val cePerTick = blueCE + redCE + greenCE + whiteCE

                val efficiency = energy/cePerTick
                //"青レーザー本数: $blueCount \n緑レーザー本数: $greenCount \n赤レーザー本数: $redCount \n白レーザー本数: $whiteCount \n総エネルギー量: ${Util().roundAndString(energy,4)}\n消費エネルギー: ${Util().roundAndString(cePerTick,2)} CE\\t\n変換効率: ${Util().roundAndString(efficiency,4)}"
                val eb = EmbedBuilder()
                eb.setTitle("エネルギー量: **${Util().roundAndString(energy,4)}/t**")
                eb.setDescription("変換効率: $${Util().roundAndString(efficiency,4)}\n消費エネルギー: ${Util().roundAndString(cePerTick,2)} CE/t")
                eb.addField("青レーザー本数","$blueCount 本",false)
                eb.addField("緑レーザー本数","$greenCount 本",false)
                eb.addField("赤レーザー本数","$redCount 本",false)


                val max = allRGBLaserCount/max(redCount, max(greenCount,blueCount))
                eb.setColor(Color(255*redCount/allRGBLaserCount*max,255*greenCount/allRGBLaserCount*max,255*blueCount/allRGBLaserCount*max))

                event.replyEmbeds(eb.build()).queue()
            }
        }
    }
}

fun main () {
    val r = 4.0
    val g = 8.0
    val b = 2.0

    val all = r + g + b

    val maxValue = max(r, max(g,b))
    val max = all/maxValue

    println("r:${255*r/all*max}, g:${255*g/all*max}, b:${255*b/all*max}")
}
package Homework

import java.io.File

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

import scala.math._
import scala.util.Sorting

object Server1 {

  def median_filter(img:BufferedImage, int: Int): BufferedImage ={
    val height = img.getHeight()
    val width = img.getWidth()
    val windowWidth = int
    val windowHeight = int
    val window: Array[Int] = new Array[Int](int*int)
    val edgx= (int/2)
    val edgy = (int/2)
    val out = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

    for (x <- edgx until width-edgx){
      for (y <- edgy until height-edgy){
        var i =0
        for (fx <- 0 until windowWidth){
          for (fy <- 0 until windowHeight){
            window(i) = img.getRGB(x + fx - edgx, y + fy - edgy)
            i += 1
          }
        }

        //sort entries in window[]
        Sorting.quickSort(window)
        out.setRGB(x,y, window((int*int) / 2))
      }

    }



    println("So far so good in 1")
    return out
  }


}


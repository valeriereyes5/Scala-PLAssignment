package Homework
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import java.io.File

import javax.imageio.ImageIO
import java.awt.image.BufferedImage



import scala.util.Sorting

object Server2 {



  def collectparallel(img: BufferedImage, windowSize: Int,startx: Int, finishx: Int, outImg: BufferedImage): Unit ={
    val width = img.getWidth()
    val height = img.getHeight()
    val out = outImg
    val window: Array[Int] = new Array[Int](windowSize*windowSize)
    val edgex = (windowSize/2)
    val edgey = (windowSize/2)
    var start: Int = startx
    var finish: Int = finishx


    if(start ==0){
      start= start+edgex
    }
    if(finish==width){
      finish=finish-edgex
    }

    for(x<- edgey until finish){
      for(y<- edgey until height-edgey){
        var i =0

        for(fx<-0 until windowSize){
          for (fy<-0 until windowSize){
            window(i) = img.getRGB(x+fx-edgex, y +fy-edgey)
            i+=1
          }
        }

        Sorting.quickSort(window)
        out.setRGB(x,y,window((windowSize*windowSize)/2))

      }
    }

  }
  def median_filterP(img: BufferedImage, int:Int): BufferedImage={

    val width = img.getWidth()
    val height = img.getHeight()
    val out = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB)
    val future1 = Future{
      collectparallel(img,int,0,width/4,out)
    }

    val future2 = Future{
      collectparallel(img,int,width/4,width/2,out)
    }

    val future3 = Future{
      collectparallel(img,int, width/2,(3*width)/4,out)
    }

    val future4 = Future{
      collectparallel(img,int, (3*width)/4,width,out)
    }

    while(!future1.isCompleted || !future2.isCompleted || !future3.isCompleted || !future4.isCompleted){
      val wow = 1
    }

    ImageIO.write(out, "jpg", new File("ParallelImg.jpg"))
    return out
  }


}

package Homework

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Image
import java.io._

import javax.swing._
import javax.swing.filechooser.FileNameExtensionFilter
import java.awt._
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.File

import akka.actor.Actor
import javax.swing.JFrame



  object Client extends JFrame {
    var label: JLabel = null
    var button: JButton = null


    ///////Works with the JLabel and does the action of looking for the image
    def Client() {

      super.setTitle("Set Picture Into A JLabel Using JFileChooser In Java")
      button = new JButton("Browse")
      button.setBounds(300, 300, 100, 40)
      label = new JLabel
      label.setBounds(10, 10, 670, 250)
      add(button)
      add(label)
      button.addActionListener(new ActionListener() {
        override def actionPerformed(e: ActionEvent): Unit = {
          val file: JFileChooser = new JFileChooser
          file.setCurrentDirectory(new File(System.getProperty("user.home")))
          //filter the files
          val filter: FileNameExtensionFilter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png")
          file.addChoosableFileFilter(filter)
          val result: Int = file.showSaveDialog(null)
          //if the user click on save in Jfilechooser
          if (result == JFileChooser.APPROVE_OPTION) {
            val selectedFile: File = file.getSelectedFile
            val path: String = selectedFile.getAbsolutePath
            ///send it to the filter
            val img = ImageIO.read(selectedFile)
            /////////// // /////////// Sends the image that the client chose to be sent to the server
            ImageIO.write(filters(img), "jpg", new File("SerialImg.jpg"))
            label.setIcon(ResizeImage("SerialImg.jpg"))

          }
          else {
            if (result == JFileChooser.CANCEL_OPTION) {
              System.out.println("No File Select")
            }
          }
        }

        display()
      })
    }

    def display() = {

      setLayout(null)
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
      setLocationRelativeTo(null)
      setSize(700, 400)
      setVisible(true)
    }


    // Method to resize imageIcon with the same size of a Jlabel
    def ResizeImage(ImagePath: String): ImageIcon = {
      val MyImage: ImageIcon = new ImageIcon(ImagePath)
      val img: Image = MyImage.getImage
      val newImg: Image = img.getScaledInstance(label.getWidth, label.getHeight, Image.SCALE_SMOOTH)
      val image: ImageIcon = new ImageIcon(newImg)
      return image
    }


    def time[R](block: => R): R = {
      val t0 = System.nanoTime()
      val result = block // call-by-name
      val t1 = System.nanoTime()

      println("Elapsed time:" + (t1 - t0) + "ns")
      result
    }


    ////PART OF THE CLIENT THAT CALL THE FILTERS
    def filters(img: BufferedImage): BufferedImage = {
      val result1 = Server1.median_filter(img, 10)
      time {
        Server2.median_filterP(img, 10)
      }
      val result = Server2.median_filterP(img, 10)
      time {
        Server2.median_filterP(img, 10)
      }
      return result
    }
    def main(args: Array[String]): Unit = {
      Client

    }




  }






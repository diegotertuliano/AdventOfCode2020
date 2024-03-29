package Solutions

import Utils.Fs2.readFile
import Utils.IOFunctions.putStrLn
import cats.effect._

import scala.annotation.tailrec

object Day01 extends IOApp {

  def findTwoSum(totalSum: Int, input: List[Int]): Option[Int] = {

    @tailrec
    def go(xs: List[Int], set: Set[Int] = Set.empty): Option[Int] =
      xs match {
        case x :: xs =>
          val difference = totalSum - x

          if (set.contains(difference))
            Some(x * difference)
          else
            go(xs, set + x)
        case Nil => None
      }

    go(input)

  }

  def findThreeSum(totalSum: Int, input: List[Int]): Option[Int] = {

    @tailrec
    def go(xs: List[Int]): Option[Int] =
      xs match {
        case x :: xs =>
          val difference = totalSum - x
          val maybeTwoSum = findTwoSum(difference, input)

          maybeTwoSum match {
            case Some(y) => Some(x * y)
            case None    => go(xs)
          }

        case Nil => None
      }

    go(input)

  }

  override def run(args: List[String]): IO[ExitCode] =
    for {
      input <- readFile("input/Day01").map(_.toInt).compile.toList
      _ <- putStrLn(findTwoSum(2020, input))
      _ <- putStrLn(findThreeSum(2020, input))
    } yield ExitCode.Success
}

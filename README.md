# CS61B Spring 2021
## 简介
记录所有我在CS61B Spring 2021中的homeworks, labs以及projects。
## 进度
#### Homeworks
- [x] hw0
- [ ] hw1
- [ ] hw2
- [ ] hw3
#### Labs
- [x] lab1
- [x] lab2setup
- [x] lab2
- [x] lab3
- [ ] lab4
- [ ] lab5
- [ ] lab6
- [ ] lab7
- [ ] lab8
#### Projects
- [x] proj0
- [x] proj1
- [ ] proj1ec
- [ ] proj2
- [ ] proj3
## Homeworks
## Labs

#### Lab2 setup

又是糟心的环境配置。

执行 `git submodule update --init` 下载Library。

下载完Library后，maven无法解析那些jar包，因为我们的maven仓库没有设置为`javalib`。

在 `File` > `Settings` > `Build……`  > `Maven` 中按照如下图设置。

![image-20241014225818828](README.assets/image-20241014225818828.png)

这样`pom.xml`就可以正常解析jar包了。

#### Lab2 JUnit Tests and Debugging

题目都比较简单，就不再赘述了。

#### Lab3 Timing Tests and Randomized Comparison Tests

一个十分实用对报错进行Debug的技巧。

在 `Run` > `View Breakpoints` 有可以断点到某个报错类型，例如我们要断点到数组边界报错，则可以

```java
this instanceof java.lang.ArrayIndexOutOfBoundsException
```

![image-20241015234307093](README.assets/image-20241015234307093.png)

#### Lab4 Git and Debugging

##### Git

因为不是参与此课程的学生，所以我们可以手动去更改我们自己github上的远程仓库中`lab1/Collatz.java`的代码为

```java
    /** Buggy implementation of nextNumber! */
    public static int nextNumber(int n) {
        if (n  == 128) {
            return 1;
        } else if (n == 5) {
            return 3 * n + 1;
        } else {
            return n * 2;
        }
    }
```

然后再 `git pull origin main` 来制造 `merge conflict`，如下所示，将HEAD部分的代码删除

```java
<<<<<<< HEAD
        if (n % 2 == 0) {
            n = n / 2;
        } else {
            n = 3 * n + 1;
        }
        return n	;
    }
=======
        if (n  == 128) {
            return 1;
        } else if (n == 5) {
            return 3 * n + 1;
        } else {
            return n * 2;
        }
>>>>>>> d82d813b7cda6b51e4243758dbbc3fc46640b3fe
```

 之后就可以按照步骤去做就可以了。

```bash
# Solve merge conflict
git add .\lab1\Collatz.java
git commit -m "Solve merge conflict."
git pull origin main

# Back to the correct Collatz.java
git checkout <Hash of your lab1\'s implement>
git checkout main
git checkout <Hash of your lab1\'s implement> -- .\lab1\Collatz.java
git commit -m "Back to the correct Collatz.java"
git push origin main
```

学习了Git的原理后，感觉跟区块链其实十分相似。

##### Debugging

Debug后可以发现传递的Integer类型到了128时，使用`==`判断时就返回false，说明已经不是一个对象了，这里应该使用Integer自己实现的`equals`。

详细的细节可以参考 https://zhuanlan.zhihu.com/p/368421654

## Projects

#### Project 0: 2048

- **规则要点**
  - 一开始，随机添加一个2或4的方块，75%的概率为2，25%的概率为4。
  - 在一次移动中完成合并的方块，不会再次进行合并。
  - 三个相邻的相同大小的方块，前两个进行合并。
  - 若一次移动后没有发生合并，将不会生成新的方块，否则将随机生成一个方块。
  - 下方有当前成绩（Score）显示，成绩（Score）为每次合并的值的和，例如一次移动中，2+2合并为4，4+4合并为8，则成绩（Score）增加12。
  - 结束条件：
    - 要么方块填满且每一个方块相邻的上下左右没有相同大小的方块。
    - 或是任意一个方块的大小大于等于2048
  - 当一次游戏结束后，需要更新最好成绩（Max Score）

- **Enum Types**

  - `Enum Types` 总的来说就是一个常量集合，在JAVA中也相当于一个类，也可以包含方法和字段。

  - JAVA 的 `Enum Types` 隐式继承至 `java.lang.Enum` ，并且JAVA不支持状态的多重继承，所以 `Enum Types`无法 `extend ` 其他任何的类。

  - `Enum Types`的具体例子`Side`

    ```java
    package game2048;
    
    /** Symbolic names for the four sides of a board.
     *  @author P. N. Hilfinger */
    public enum Side {
        /** The parameters (COL0, ROW0, DCOL, and DROW) for each of the
         *  symbolic directions, D, below are to be interpreted as follows:
         *     The board's standard orientation has the top of the board
         *     as NORTH, and rows and columns (see Model) are numbered
         *     from its lower-left corner. Consider the board oriented
         *     so that side D of the board is farthest from you. Then
         *        * (COL0*s, ROW0*s) are the standard coordinates of the
         *          lower-left corner of the reoriented board (where s is the
         *          board size), and
         *        * If (c, r) are the standard coordinates of a certain
         *          square on the reoriented board, then (c+DCOL, r+DROW)
         *          are the standard coordinates of the squares immediately
         *          above it on the reoriented board.
         *  The idea behind going to this trouble is that by using the
         *  col() and row() methods below to translate from reoriented to
         *  standard coordinates, one can arrange to use exactly the same code
         *  to compute the result of tilting the board in any particular
         *  direction. */
    
        NORTH(0, 0, 0, 1), EAST(0, 1, 1, 0), SOUTH(1, 1, 0, -1),
        WEST(1, 0, -1, 0);
    
        /** The side that is in the direction (DCOL, DROW) from any square
         *  of the board.  Here, "direction (DCOL, DROW) means that to
         *  move one space in the direction of this Side increases the row
         *  by DROW and the colunn by DCOL.  (COL0, ROW0) are the row and
         *  column of the lower-left square when sitting at the board facing
         *  towards this Side. */
        Side(int col0, int row0, int dcol, int drow) {
            this.row0 = row0;
            this.col0 = col0;
            this.drow = drow;
            this.dcol = dcol;
        }
    
        /** Returns the side opposite of side S. */
        static Side opposite(Side s) {
            if (s == NORTH) {
                return SOUTH;
            } else if (s == SOUTH) {
                return NORTH;
            } else if (s == EAST) {
                return WEST;
            } else {
                return EAST;
            }
        }
    
        /** Return the standard column number for square (C, R) on a board
         *  of size SIZE oriented with this Side on top. */
        public int col(int c, int r, int size) {
            return col0 * (size - 1) + c * drow + r * dcol;
        }
    
        /** Return the standard row number for square (C, R) on a board
         *  of size SIZE oriented with this Side on top. */
        public int row(int c, int r, int size) {
            return row0 * (size - 1) - c * dcol + r * drow;
        }
    
        /** Parameters describing this Side, as documented in the comment at the
         *  start of this class. */
        private int row0, col0, drow, dcol;
    
    };
    ```

    - 可以看到常量是最先声明的，`,` 间隔，`；` 收尾。
    - 构造方法必须是私有的，无法通过 `new` 等方法触发构造方法。

- 我们可能用到的。

  - `Tile.value()`
  - `Board.setViewingPerspective` `Board.tile` `Board.move`

- 我们所需要实现的方法。

  - `emptySpaceExists`
  - `maxTileExists`
  - `atLeastOneMoveExists`
  - `tilt`

- **public static boolean emptySpaceExists(Board b)**

  循环遍历判断即可。

  ```java
      /** Returns true if at least one space on the Board is empty.
       *  Empty spaces are stored as null.
       * */
      public static boolean emptySpaceExists(Board b) {
          // TODO: Fill in this function.
          for (int i = 0; i < b.size(); i++) {
              for (int j = 0; j < b.size(); j++) {
                  if (b.tile(i, j) == null) {
                      return true;
                  }
              }
          }
          return false;
      }
  ```

- **public static boolean maxTileExists(Board b)**

  这也是循环遍历判断即可，但要注意判断`b.tile(i, j) != null`，一开始没有注意这个问题。

  ```java
      /**
       * Returns true if any tile is equal to the maximum valid value.
       * Maximum valid value is given by MAX_PIECE. Note that
       * given a Tile object t, we get its value with t.value().
       */
      public static boolean maxTileExists(Board b) {
          for (int i = 0; i < b.size(); i++) {
              for (int j = 0; j < b.size(); j++) {
                  if (b.tile(i, j) != null && b.tile(i, j).value() >= MAX_PIECE) {
                      return true;
                  }
              }
          }
          return false;
      }
  ```

- **public static boolean atLeastOneMoveExists(Board b)**

  分两种情况：

  - 先是判断是否有空块，就直接用实现好的 `emptySpaceExists` 进行判断即可。
  - 若没有空块，则判断是否有相邻相同大小的方块，也是两层遍历，但行只需遍历到 `b.size() - 1` 即可。

  ```java
      /**
       * Returns true if there are any valid moves on the board.
       * There are two ways that there can be valid moves:
       * 1. There is at least one empty space on the board.
       * 2. There are two adjacent tiles with the same value.
       */
      public static boolean atLeastOneMoveExists(Board b) {
          // 1. There is at least one empty space on the board.
          if (emptySpaceExists(b)) {
              return true;
          }
          // 2. There are two adjacent tiles with the same value.
          for (int i = 0; i < b.size(); i++) {
              for (int j = 0; j < b.size() - 1; j++) {
                  if (i + 1 < b.size() && b.tile(i, j).value() == b.tile(i + 1, j).value()) {
                      return true;
                  } else if (b.tile(i, j).value() == b.tile(i, j + 1).value()) {
                      return true;
                  }
              }
          }
          return false;
      }
  ```

- **public boolean tilt(Side side)**  （Main Task: Building the Game Logic）

  按列按行遍历，行是从上往下遍历，分为三种情况：

  - 当前方块前没有方块.
  - 当前方块前面的第一个方块的大小与当前方块相同.
  - 当前方块前面的第一个方块的大小与当前方块不同.

  遇到的问题：

  - 一开始 `this.score += board.tile(l, sub_r).value();` 写成了 `this.score += targetTile.value();`，导致`this.score`增加的方块大小依然是原来的方块大小，因为每`move`一次时，会再创建一个新的`tile`赋值给`board.values`，而不是直接改变`tile.value`，导致`targetTile.value()` 并不会随之变化。

  ```java
      /** Tilt the board toward SIDE. Return true iff this changes the board.
       *
       * 1. If two Tile objects are adjacent in the direction of motion and have
       *    the same value, they are merged into one Tile of twice the original
       *    value and that new value is added to the score instance variable
       * 2. A tile that is the result of a merge will not merge again on that
       *    tilt. So each move, every tile will only ever be part of at most one
       *    merge (perhaps zero).
       * 3. When three adjacent tiles in the direction of motion have the same
       *    value, then the leading two tiles in the direction of motion merge,
       *    and the trailing tile does not.
       * */
      public boolean tilt(Side side) {
          boolean changed;
          changed = false;
          int boardSize = this.board.size();
          this.board.setViewingPerspective(side);
          for (int l = 0; l < boardSize; l++) {
              boolean isMerge = false; // Record whether last tile is merged.
              for (int r = boardSize - 2; r >= 0; r--) {
                  int sub_r = r + 1;
                  Tile curTile = this.board.tile(l, r);
                  if (curTile == null) continue;
                  // The target tile is what current tile want to move to
                  // if the target tile is not null or is just the last one.
                  Tile targetTile = null;
                  while (targetTile == null && sub_r < boardSize) {
                      targetTile = this.board.tile(l, sub_r);
                      sub_r += 1;
                  }
                  sub_r -= 1; // For easier use of sub_r.
                  // Three cases of current tile:
                  // 1. Tiles in front of current tile are all null.
                  // 2. The first in front of current tile has the same value.
                  // 3. The first in front of current tile has not the same value.
                  if (targetTile == null) {
                      this.board.move(l, sub_r, curTile);
                      changed = true;
                  } else if (!isMerge && targetTile.value() == curTile.value()) {
                      this.board.move(l, sub_r, curTile);
                      this.score += board.tile(l, sub_r).value();
                      isMerge = true;
                      changed = true;
                  } else {
                      if (sub_r - 1 != r) {
                          this.board.move(l, sub_r - 1, curTile);
                          changed = true;
                      }
                      isMerge = false;
                  }
              }
          }
          this.board.setViewingPerspective(Side.NORTH);
          checkGameOver();
          if (changed) {
              setChanged();
          }
          return changed;
      }
  ```

- **注意**

  当我们实现代码后启动游戏，可能会发现方块并不会进行移动。是因为我们按上下左右键时，程序获取到的指令分别是"向上箭头"、"向下箭头"、"向左箭头"、"向右箭头"。需要在`Game.playGame`以及`Game.keyToSide`添加对应的指令。

  ```java
      /** Clear the board and play one game, until receiving a quit or
       *  new-game request.  Update the viewer with each added tile or
       *  change in the board from tilting. */
      void playGame() {
          _model.clear();
          _model.addTile(getValidNewTile());
          while (_playing) {
              if (!_model.gameOver()) {
                  _model.addTile(getValidNewTile());
                  _model.notifyObservers();
              }
  
              boolean moved;
              moved = false;
              while (!moved) {
                  String cmnd = _source.getKey();
                  switch (cmnd) {
                      case "Quit":
                          _playing = false;
                          return;
                      case "New Game":
                          return;
                      case "Up": case "Down": case "Left": case "Right":
                      case "\u2190": case "\u2191": case "\u2192": case "\u2193":
                      case "向上箭头": case "向下箭头": case "向左箭头": case "向右箭头": // 添加对应的指令
                          if (!_model.gameOver() && _model.tilt(keyToSide(cmnd))) {
                              _model.notifyObservers(cmnd);
                              moved = true;
                          }
                          break;
                      default:
                          break;
                  }
  
              }
          }
      }
  ```

  ```java
      /** Return the side indicated by KEY ("Up", "Down", "Left",
       *  or "Right"). */
      private Side keyToSide(String key) {
          switch (key) {
          	// 添加对应的指令
              case "Up": case "\u2191": case "向上箭头":
                  return NORTH;
              case "Down": case "\u2193": case "向下箭头":
                  return SOUTH;
              case "Left": case "\u2190": case "向左箭头":
                  return WEST;
              case "Right": case "\u2192": case "向右箭头":
                  return EAST;
              default:
                  throw new IllegalArgumentException("unknown key designation");
          }
      }
  ```

  #### Project 1 Data Structures
  
  Through the Fire and Flames还是很好听的，哈哈。


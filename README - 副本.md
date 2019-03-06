# EtherLet
Molbile Application Development Final Project

## Git 使用规范

1. 分支说明
- master分支：不要管
- develop分支：稳定的开发分支，切忌**直接往develop分支提交代码**。
- feature/***：各个功能的开发分支。**请只在自己的功能分支下工作**。

2. 初次使用clone仓库
```
git clone git@github.com:byrantwithyou/DBCourseProject-Poi.git
```
3. 在develop分支的基础上创建新的功能分支feature/X

```
git checkout develop

git checkout -b feature/X
```

4. feature/X开发完成，首先要更新本地develop分支
```
git checkout deveop
git pull origin develop
```
5. 切换到feature/X分支，进行rebase操作
```
git checkout feature/X
git rebase develop（如果这时候有冲突，只能联系写这部分代码的人，手动合并。）
```
6. 将feature/X推入Github
```
git push origin feature/X
```
7. 去GitHub上发Pull Request请求:feature/X - > develop(**发完Request QQ上提前告诉我一声**)


## 代码规范
1. 类名：首字母大写、驼峰
2. 变量、属性、方法：首字母小写、驼峰
3. 注释：尽量采用Java Doc规范

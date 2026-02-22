# MelancesCore PATCH

Bu zip, mevcut repo içine **üstüne yazılarak** uygulanmak için hazırlanmıştır.

## Silinecek dosyalar (git rm)
- src/main/java/com/melances/modules/ui/UiService.java
- src/main/java/com/melances/modules/ui/UiModule.java
- src/main/java/com/melances/modules/menu/MenuModule.java
- src/main/java/com/melances/modules/rank/RankModule.java
- src/main/java/com/melances/core/module/Module.java
- src/main/java/com/melances/core/module/MelancesModule.java
- src/main/java/com/melances/core/bootstrap/MelancesCorePlugin.java
- src/main/java/com/melances/core/internal/SqliteDb.java

Repo durumuna göre isimler değişikse, `git status` ile kontrol et.

## Uygulama adımları (PowerShell örneği)
1) Repo kökünde bu zip'i aç ve dosyaların üstüne yaz.
2) Sonra:

```powershell
git rm -f src/main/java/com/melances/modules/ui/UiService.java
git rm -f src/main/java/com/melances/modules/ui/UiModule.java
git rm -f src/main/java/com/melances/modules/menu/MenuModule.java
git rm -f src/main/java/com/melances/modules/rank/RankModule.java
git rm -f src/main/java/com/melances/core/module/Module.java
git rm -f src/main/java/com/melances/core/module/MelancesModule.java
git rm -f src/main/java/com/melances/core/bootstrap/MelancesCorePlugin.java
git rm -f src/main/java/com/melances/core/internal/SqliteDb.java

git add .
git commit -m "core: iskelet mimari + debug + ui teşhis + testmenu"
git push
```

3) Test:
```powershell
./gradlew clean build
```

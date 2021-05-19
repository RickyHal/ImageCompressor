# ImageCompress
[![](https://jitpack.io/v/RickyHal/ImageCompressor.svg)](https://jitpack.io/#RickyHal/ImageCompressor)

中文 | [English](/README.md)
一个基于libjpeg实现的Android图片压缩库，可在保证质量的情况下极致压缩图片大小，具体压缩效果见下文。

实现原理见[这位大佬的博客](https://juejin.cn/post/6844904014442659853#heading-6)

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.RickyHal:ImageCompressor:1.0'
	}


## 怎么使用

Bitmap压缩得到Bitmap
```Java
  /**
     * Compress bitmap API
     *
     * @param context
     * @param srcBitmap src bitmap
     * @param quality   compress quality 0-100
     * @param format    compress format
     * @return dst bitmap
     */
    public static Bitmap compressBitmap(Context context, Bitmap srcBitmap, int quality, @Nullable Bitmap.CompressFormat format);
```

```kotlin
   // compress
   val dstBitmap = CompressUtil.compressBitmap(BaseApplication.gContext, srcBitmap, quality, Bitmap.CompressFormat.JPEG)
```

Bitmap压缩得到文件，可用于保存压缩结果

```Java
    // API
    public native static int compressBitmap(Bitmap bitmap, int quality, String destFile);
```

```kotlin
    // compress
    CompressUtil.compressBitmap(srcBitmap, quality, destPath)
```

# 压缩效果

* 原图（2080*4413）5.56MB
![原图](/results/origin.jpg)

* 压缩质量90%（2080*4413）1.87MB 耗时977ms
![压缩质量90%](/results/quality90.jpg)

* 压缩质量80%（2080*4413）1.18MB 耗时916ms
![压缩质量80%](/results/quality80.jpg)

* 压缩质量70%（2080*4413）918KB 耗时902ms
![压缩质量70%](/results/quality70.jpg)

* 压缩质量60%（2080*4413）735KB 耗时870ms
![压缩质量60%](/results/quality60.jpg)

* 压缩质量50%（2080*4413）622KB 耗时857ms
![压缩质量50%](/results/quality50.jpg)

* 压缩质量40%（2080*4413）519KB 耗时858ms
![压缩质量40%](/results/quality40.jpg)

* 压缩质量30%（2080*4413）420KB 耗时839ms
![压缩质量30%](/results/quality30.jpg)

* 压缩质量20%（2080*4413）315KB 耗时828ms
![压缩质量20%](/results/quality20.jpg)

* 压缩质量10%（2080*4413）199KB 耗时819ms
![压缩质量10%](/results/quality10.jpg)

* 压缩质量5%（2080*4413）129KB 耗时809ms
![压缩质量5%](/results/quality5.jpg)

* 压缩质量1%（2080*4413）91.8KB 耗时808ms
![压缩质量1%](/results/quality1.jpg)


## License

> ```
> Copyright 2021 RickyHal
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
>
>    http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
> ```

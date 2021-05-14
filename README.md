# ImageCompress
[![](https://jitpack.io/v/RickyHal/ImageCompressor.svg)](https://jitpack.io/#RickyHal/ImageCompressor) ![Workflow result](https://github.com/RickyHal/ImageCompressor/workflows/Check/badge.svg)

An Android image compression library based on libjpeg can efficiently compress the pictures in mobile phones [Chinese](https://github.com/RickyHal/ImageCompressor/README.zh.md)

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


## How to use

bitmap compress to bitmap
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

or bitmap compress to file

```Java
    // API
    public native static int compressBitmap(Bitmap bitmap, int quality, String destFile);
```

```kotlin
    // compress
    CompressUtil.compressBitmap(srcBitmap, quality, destPath)
```


* Original picture（2080*4413）5.56MB
![Original picture](https://github.com/RickyHal/ImageCompressor/results/origin.jpg)

* quality90%（2080*4413）1.87MB 耗时977ms
![quality90%](https://github.com/RickyHal/ImageCompressor/results/quality90.jpg)

* quality80%（2080*4413）1.18MB 耗时916ms
![quality80%](https://github.com/RickyHal/ImageCompressor/results/quality80.jpg)

* quality70%（2080*4413）918KB 耗时902ms
![quality70%](https://github.com/RickyHal/ImageCompressor/results/quality70.jpg)

* quality60%（2080*4413）735KB 耗时870ms
![quality60%](https://github.com/RickyHal/ImageCompressor/results/quality60.jpg)

* quality50%（2080*4413）622KB 耗时857ms
![quality50%](https://github.com/RickyHal/ImageCompressor/results/quality50.jpg)

* quality40%（2080*4413）519KB 耗时858ms
![quality40%](https://github.com/RickyHal/ImageCompressor/results/quality40.jpg)

* quality30%（2080*4413）420KB 耗时839ms
![quality30%](https://github.com/RickyHal/ImageCompressor/results/quality30.jpg)

* quality20%（2080*4413）315KB 耗时828ms
![quality20%](https://github.com/RickyHal/ImageCompressor/results/quality20.jpg)

* quality10%（2080*4413）199KB 耗时819ms
![quality10%](https://github.com/RickyHal/ImageCompressor/results/quality10.jpg)

* quality5%（2080*4413）129KB 耗时809ms
![quality5%](https://github.com/RickyHal/ImageCompressor/results/quality5.jpg)

* quality1%（2080*4413）91.8 耗时808ms
![quality1%](https://github.com/RickyHal/ImageCompressor/results/quality1.jpg)


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

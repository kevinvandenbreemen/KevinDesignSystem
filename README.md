# KevinDesignSystem
Attempt at creating a personal UI design system library in Android

# How to Add it to your Project
I've used Jitpack for adding this to my projects.  To add this first make sure jitpack is one of your repositories in your root build.gradle:

Add this to the repositories section:

```
maven { url 'https://jitpack.io' }
```

Next add the actual dependency itself.  In your module build.gradle add this under dependencies:

```
implementation 'com.github.kevinvandenbreemen:KevinDesignSystem:[release_num]'
```



# The Components

## Important
In the onCreate() method for any activity you override be sure to call setContentView() first before calling super.onCreate().  This will give the superclass a chance to set up the UI components.

## KDSSystemActivity

![](./docs/res/KDSSystemActivity.png)

### How to use It
Add an activity to your project and have it inherit from KDSSystemActivity.

Make sure your activity supports device rotation so be sure to include

```
android:configChanges="orientation"
```

in the activity declaration in AndroidManifest.xml

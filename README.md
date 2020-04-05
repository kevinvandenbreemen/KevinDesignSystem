# KevinDesignSystem
Attempt at creating a personal UI design system library in Android



# The Components

## Important
In the onCreate() method for any activity you override be sure to call setContentView() first before calling super.onCreate().  This will give the superclass a chance to set up the UI components.

## KDSSystemActivity

### How to use It
Add an activity to your project and have it inherit from KDSSystemActivity.

Make sure your activity supports device rotation so be sure to include

```
android:configChanges="orientation"
```

in the activity declaration in AndroidManifest.xml


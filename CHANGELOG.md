Change Log
==========

Version 1.1.0 *(2016-02-06)*
----------------------------
**IMPORTANT! `groupId` has been changed! When adding dependencies use:**

```groovy
compile 'com.bartoszlipinski.rxanimationbinding:rxanimationbinding:X.X.X'
```

*instead of:*

```groovy
compile 'com.bartoszlipinski:rxanimationbinding:X.X.X'
```
---

 * New module!
    * `rxanimationbinding-support-v4` for the `ViewPropertyAnimatorCompat` from the 'Support Library'
 * Added annotations `@CheckResult` and `@NonNull`
 * Binding classes corrected (added `final` modifier + private constructors)
 
Version 1.0.0 *(2016-01-25)*
----------------------------

 * AppCompat removed from the library
 
Version 1.0.0-beta2 *(2016-01-24)*
----------------------------

*Use `compile 'com.bartoszlipinski:rxanimationbinding:1.0.0-beta2@aar'` (with `@aar` suffix)*

 * Generic parameters corrected
 * OnUnsubscribe threads corrected

Version 1.0.0-beta1 *(2016-01-22)*
----------------------------

*Use `compile 'com.bartoszlipinski:rxanimationbinding:1.0.0-beta1@aar'` (with `@aar` suffix)*

Initial release.

package dev.reprator.haat.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import dev.reprator.haat.R

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val sourceFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Source Sans Pro"),
        fontProvider = provider,
    )
)

val robotoFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Roboto"),
        fontProvider = provider,
    )
)


val baseline = Typography()

val haatTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = robotoFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = robotoFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = robotoFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = robotoFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = robotoFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = robotoFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = robotoFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = robotoFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = robotoFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = robotoFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = robotoFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = robotoFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = robotoFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = robotoFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = robotoFontFamily),
)

/*val haatTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
)*/

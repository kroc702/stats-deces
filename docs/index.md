---
---

<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datasource@0.1.0"></script>
<script src="main.js"></script>
<style>
#main_content {
    max-width: fit-content;
</style>


## Décès par an
<div><canvas id="DecesParAn"></canvas></div>
	
Le nombre et le taux de décès annuel enregistré par l'insee au cours des 30 dernières années. Le taux de la dernière année est extrapolé sur l'année complète.

L'écart entre le nombre et le taux s'explique par l'augmentation de la population.

L'écart entre le taux et le taux normalisé s'explique par le veillissement de la population.

	
## Décès par semaine
<div><canvas id="DecesParSemaine"></canvas></div>
	
Même chiffre que ci-dessus mais par semaine sur les dix dernières années.

## Décès par age
<div><canvas id="DecesParAge"></canvas></div>
	
La taux de décès pour chaque age.

## Décès COVID par tranche d'age
<div><canvas id="DecesCovidParAge"></canvas></div>

nombre de décès lors d’une hospitalisation entre le 01/03/2020 et le 04/07/2021.

source: [Santé public france, Point épidémiologique hebdomadaire n° 71 du 08 juillet 2021. p21](https://www.santepubliquefrance.fr/content/download/358653/3091329)

## Activité hospitalière en 2020
[![Hospitalisation en 2020](/images/Hospitalisation2020.png)](https://www.scansante.fr/applications/analyse-activite-nationale)

## Sources

- [La liste des décès](https://www.data.gouv.fr/fr/datasets/fichier-des-personnes-decedees/)
- [pyramide des ages](https://www.insee.fr/fr/outil-interactif/5014911/pyramide.htm)
- [pyramide des ages 1991-2020](https://www.insee.fr/fr/statistiques/3312958)
- [pyramide des ages 2021](https://www.insee.fr/fr/statistiques/2381472#graphique-figure1)
- [Nombre quotidien de nouveaux décès avec diagnostic COVID-19 déclarés en 24h](https://geodes.santepubliquefrance.fr/#c=indicator&i=covid_hospit_incid.incid_dc&s=2021-07-19&t=a01&view=map2) (cf syntèse)


Dernière mise à jours: 28/06/2022

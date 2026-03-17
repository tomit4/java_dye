# From RGB To HSL:

Values must be in range $R, G, B \in [0, 1]$.

With maximum component (_i.e._ value)

$$ X_{max} := max(R, G, B) =: V $$

and minimum component

$$ X_{min} := min(R, G, B) = V - C $$

range(_i.e._ chroma)

$$ C := X_{max} - X_{min} = 2(V - L) $$

and mid-range (_i.e._ lightness)

$$ L := mid(R, G, B) = \frac{X_{max} - X_{min}}{2} = V - \frac{C}{2} $$

we get common hue:

$$
H :=
\begin{cases}
0, & \text{if } C = 0 \\
60\degree \cdot \left(\frac{G - B}{C} \mod 6\right), & \text{if } V = R \\
60\degree \cdot \left(\frac{B - R}{C} + 2\right), & \text{if } V = G
60\degree \cdot \left(\frac{R - G}{C} + 4\right), & \text{if } V = B
\end{cases}
$$

and distinct saturations:

$$
S_V :=
\begin{cases}
0, & \text{if } V = 0 \\
\frac{C}{V}, & \text{otherwise}
\end{cases}
$$

$$
S_L :=
\begin{cases}
0, & \text{if } L = 0 \text{ or } L = 1\\
\frac{C}{1 - \left|2V - C - 1 \right|} = \frac{2(V - L)}{1 - \left|2L - 1\right|} = \frac{V - L}{\min(L, 1 - L)}, & \text{otherwise}
\end{cases}
$$

## HSL to RGB

Given a color with hue $H \in \left[0\degree, 360\degree\right)$, saturation
$S_L \in [0, 1]$, and lightness $L \in [0, 1]$, we first find chroma:

$$ C = \left(1 - \left|2L - 1\right|\right) \times S_L $$

Then we can find a point $(R_1, G_1, B_1)$ along the bottom three faces of the
RGB cube, with the same hue and chroma as our color (using the intermediate
value $X$ for the second largest component of this color):

$$ H' = \frac{H}{60\degree} $$

$$ X = C \times \left(1 - \left|H' \mod 2 - 1\right|\right) $$

In the above equation, the notation $H' \mod 2$ refers to the remainder of the
[Euclidean division](https://en.wikipedia.org/wiki/Euclidean_division) of $H'$
by $2$. $H'$ is not necessarily an integer.

$$
(R_1, G_1, B_1)=
\begin{cases}
(C, X, 0) & \text{if } 0 \leq H' < 1 \\
(X, C, 0) & \text{if } 1 \leq H' < 2 \\
(0, C, X) & \text{if } 2 \leq H' < 3 \\
(0, X, C) & \text{if } 3 \leq H' < 4 \\
(X, 0, C) & \text{if } 4 \leq H' < 5 \\
(C, 0, X) & \text{if } 5 \leq H' < 6 \\
\end{cases}
$$

When $H'$ is an integer, the "neighboring" formula would yield the same result,
as $X = 0$ or $X = C$, as appropriate.

Finally, we can find $R$, $G$, and $B$ by adding the same amount to each
component, to match lightness:

$$ m = L - \frac{C}{2} $$

import type { MetadataRoute } from "next";

export default async function sitemap(): Promise<MetadataRoute.Sitemap> {
  const siteUrl = process.env.NEXT_PUBLIC_SITE_URL || "http://localhost:3000";
  const backendUrl = process.env.INTERNAL_BACK_END_URL || process.env.NEXT_PUBLIC_BACK_END_URL || "http://localhost:8080";

  const staticRoutes: MetadataRoute.Sitemap = [
    {
      url: `${siteUrl}`,
      lastModified: new Date(),
      changeFrequency: "daily",
      priority: 1.0,
    },
    {
      url: `${siteUrl}/products`,
      lastModified: new Date(),
      changeFrequency: "daily",
      priority: 0.9,
    },
    {
      url: `${siteUrl}/about`,
      lastModified: new Date(),
      changeFrequency: "monthly",
      priority: 0.5,
    },
    {
      url: `${siteUrl}/contact`,
      lastModified: new Date(),
      changeFrequency: "monthly",
      priority: 0.5,
    },
  ];

  try {
    const res = await fetch(`${backendUrl}/api/public/products?page=0&size=100`, {
      next: { revalidate: 3600 },
    });
    if (res.ok) {
      const data = await res.json();
      const productRoutes: MetadataRoute.Sitemap = (data?.content || []).map((p: { id?: string | number; productId?: string | number }) => ({
        url: `${siteUrl}/products/${p.id || p.productId}`,
        lastModified: new Date(),
        changeFrequency: "weekly",
        priority: 0.8,
      }));
      return [...staticRoutes, ...productRoutes];
    }
  } catch {
    // If backend is unreachable during static build, return static routes gracefully
  }

  return staticRoutes;
}
